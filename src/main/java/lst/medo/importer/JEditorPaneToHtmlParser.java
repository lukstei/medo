package lst.medo.importer;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JEditorPaneToHtmlParser implements ToHtmlParser {
    @Override public String parse(Path file) throws ParseException {
        Pattern htmlTrimPattern = Pattern.compile(".*<body>(.*)</body>.*", Pattern.DOTALL);
        StringWriter writer = new StringWriter();
        try {
            RTFEditorKit rtfEditorKit = new RTFEditorKit();
            HTMLEditorKit htmlEditorKit = new HTMLEditorKit();
            Document doc = rtfEditorKit.createDefaultDocument();
            rtfEditorKit.read(new FileInputStream(file.toFile()), doc, 0);
            htmlEditorKit.write(writer, doc, 0, doc.getLength());
        } catch (Exception ex) {
            //log.error("Error during converting rtf 2 html!", ex);
        }
        String html = writer.toString();
        Matcher m = htmlTrimPattern.matcher(html);

        // html content
        //html=m.group(1);

        //System.out.println(rtf+"\nrtf->html\n"+html);

        return html;
    }

    @Override public boolean canParse(Path file) {
        return file.toString().endsWith(".rtf");
    }
}
