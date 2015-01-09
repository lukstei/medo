package lst.medo.model;

import java.util.Date;

/**
 * Represents a date range.
 * Invariant condition: from <= to
 */
public class DateRange {
    Date from;
    Date to;

    public DateRange(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }
}
