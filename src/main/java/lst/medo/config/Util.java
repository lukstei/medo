package lst.medo.config;

import java.time.format.DateTimeFormatter;

public class Util {
    public static DateTimeFormatter SHORT_DATE = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
