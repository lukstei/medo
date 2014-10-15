package lst.medo.importer;

import lst.medo.dao.ArticleDao;
import lst.medo.dao.impl.JooqArticleDao;
import lst.medo.model.Article;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Importer {
    public static void main(String[] args) {
        Path path = null;
        if (args.length == 1) {
            String pathStr = args[0];
            try {
                path = Paths.get(pathStr);
            } catch (InvalidPathException e) {
                System.err.println("Invalid path " + pathStr);
                System.exit(1);
            }
        }

        if (path == null) {
            System.err.println("Usage: lst.medo.importer.Importer import-path");
            System.exit(1);
        }

        Connection conn = null;

        String userName = "postgres";
        String password = "";
        String url = "jdbc:postgresql://localhost:5432/medo";

        try {
            Class.forName("org.postgresql.Driver").newInstance();
            conn = DriverManager.getConnection(url, userName, password);

            DSLContext context = DSL.using(conn, SQLDialect.POSTGRES);

            new Importer(context).startImport(path);
        } catch (Exception e) {
            // For the sake of this tutorial, let's keep exception handling simple
            e.printStackTrace();
            System.exit(1);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ignore) {
                }
            }
        }
    }

    DSLContext mContext;
    ArticleDao mArticleDao;
    ToHtmlParser mToHtmlParser;

    public Importer(DSLContext context) {
        mContext = context;
        mArticleDao = new JooqArticleDao(context);
        mToHtmlParser = ToHtmlParser.create();
    }

    void startImport(Path path) throws IOException {
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(path)) {
            for (Path file : paths) {
                if (Files.isDirectory(file)) {
                    startImport(file);
                } else if (Files.isRegularFile(file)) {
                    if (mToHtmlParser.canParse(file)) {
                        importFile(file);
                    } else {
                        System.err.println("Warning: Unrecognized file format " + file);
                    }
                }
            }
        }
    }

    // Kommentar_2014-01-02_Standard_Gudrun Harrer
    Pattern mPattern = Pattern.compile("^(Kommentar|Interview)_([0-9]{4}-[0-9]{2}-[0-9]{2})_([^\\._]+)_?([^\\._]+)");

    void importFile(Path path) {
        String fileName = path.getFileName().toString();
        Matcher matcher = mPattern.matcher(fileName);
        if (!matcher.find()) {
            System.err.println("Warning: Unrecognized file name pattern " + fileName);
            return;
        }

        String text;
        try {
            text = mToHtmlParser.parse(path);
        } catch (ToHtmlParser.ParseException e) {
            System.err.println("Error: Importing " + path + ": " + e);
            return;
        }

        Article article = new Article();
        article.setType(matcher.group(1));
        article.setMedia(matcher.group(3));
        article.setAuthor(matcher.groupCount() >= 4 ? matcher.group(4) : null);
        article.setText(text);

        String[] dates = matcher.group(2).split("-");
        LocalDate date = LocalDate.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
        article.setDate(date);

        mArticleDao.save(article);

        System.out.println("Imported " + fileName);
    }
}
