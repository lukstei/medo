package lst.medo.importer;

import lst.medo.config.Config;
import lst.medo.dao.ArticleDao;
import lst.medo.model.Article;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConditionalOnNotWebApplication
public class Importer implements CommandLineRunner {
    @Autowired DSLContext mContext;
    @Autowired ArticleDao mArticleDao;
    ToHtmlParser mToHtmlParser =  ToHtmlParser.create();

    @Value("${path}")
    String path;

    public static void main(String... args) {
        new SpringApplicationBuilder(Importer.class, Config.class)
                .web(false)
                .run(args);
    }

    @Override public void run(String... args) throws Exception {
        if (path == null) {
            System.err.println("Usage: lst.medo.importer.Importer --path=<import dir>\n\t[--url=<datasource url>]\n\t[--user=<datasource username>]\n\t[--password=<datasource password>]");
            System.exit(1);
        }

        startImport(Paths.get(path));
    }

    /**
     * Starts the import from the given directory
     * @param path the diretory
     * @throws IOException
     */
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

    /**
     * Imports one file
     *
     * @param path file path
     */
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
