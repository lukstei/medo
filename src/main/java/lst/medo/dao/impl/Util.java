package lst.medo.dao.impl;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

class Util {
    private static final ZoneId UTC = ZoneId.of("UTC");
    public static Map<String, Integer> ARTICLE_TYPES = new HashMap<String, Integer>() {{
        put("Interview", 0);
        put("Kommentar", 1);
    }};

    public static Date toSqlDate(LocalDate date) {
        return new Date(
                date.atStartOfDay(UTC).toInstant().getEpochSecond() * 1000);
    }

    public static LocalDate fromSqlDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(UTC));
    }
}
