package lst.medo.importer;

import java.nio.file.Path;

/**
 * Defines a parser which takes a file and outputs its content in HTML.
 */
public interface ToHtmlParser {
    /**
     * @return default instance
     */
    static ToHtmlParser create() {
        return new TikaToHtmlParser();
    }

    /**
     * Parsing exception
     */
    class ParseException extends Exception {
        public ParseException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Parses a file and returns its contents as HTML.
     * @param file file to parse
     * @return html content
     * @throws ParseException
     */
    String parse(Path file) throws ParseException;

    /**
     * Determines whether the file is supported by the parser.
     *
     * @param file file to parse
     * @return true when the file can be parsed
     */
    boolean canParse(Path file);
}
