package lst.medo.importer;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class TikaToHtmlParser implements ToHtmlParser {
    private final Tika mTika;

    public TikaToHtmlParser() {
        mTika = new Tika();
        mTika.setMaxStringLength(-1);
    }

    List<Pattern> clean = Arrays.asList(
            Pattern.compile("<[bi]/>"),
            Pattern.compile(" xmlns=\".+?\"")
    );

    @Override public String parse(Path file) throws ParseException {
        try {
            Metadata metadata = new Metadata();

            StringWriter sw = new StringWriter();
            SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler handler = null;
            try {
                handler = factory.newTransformerHandler();
            } catch (TransformerConfigurationException e) {
                throw new RuntimeException(e);
            }

            handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
            //handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "no");
            handler.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "true");
            handler.setResult(new StreamResult(sw));

            mTika.getParser().parse(new FileInputStream(file.toFile()), new BodyContentHandler(handler), metadata, new ParseContext());

            String result = sw.toString();
            for (Pattern pattern : clean) {
                result = pattern.matcher(result).replaceAll("");
            }

            return result;
        } catch (org.xml.sax.SAXException | IOException | TikaException e) {
            throw new ParseException(e);
        }
    }

    @Override public boolean canParse(Path file) {
        try {
            return mTika.detect(file.toString()) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
