package lst.medo.config;

import java.time.format.DateTimeFormatter;

public class Util {
    /**
     * Short date format
     */
    public static final DateTimeFormatter SHORT_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Determines whether a string is empty or null.
     * @param s string
     * @return true when null or empty
     */
    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
