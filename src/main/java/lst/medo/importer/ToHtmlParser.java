package lst.medo.importer;

import java.nio.file.Path;

public interface ToHtmlParser {
    static ToHtmlParser create() {
        return new TikaToHtmlParser();
    }

    class ParseException extends Exception {
        public ParseException(Throwable cause) {
            super(cause);
        }
    }

    String parse(Path file) throws ParseException;
    boolean canParse(Path file);
}
