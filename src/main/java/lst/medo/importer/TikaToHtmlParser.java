package lst.medo.importer;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;

public class TikaToHtmlParser implements ToHtmlParser {
    private final Tika mTika;

    public TikaToHtmlParser() {
        mTika = new Tika();
        mTika.setMaxStringLength(-1);


    }

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
            handler.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "false");
            handler.setResult(new StreamResult(sw));

            mTika.getParser().parse(new FileInputStream(file.toFile()), handler, metadata, new ParseContext());

            return sw.toString();

            //return mTika.parseToString(file.toFile()).replaceAll("\r?\n", "<br/>");
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
