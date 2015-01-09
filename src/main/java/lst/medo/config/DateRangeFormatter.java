package lst.medo.config;

import lst.medo.model.DateRange;
import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;

import java.text.ParseException;
import java.util.*;

/**
 * This class implements a converter for our DateRange class,
 * for marshalling and unmarshalling from query string parameters.
 */
public class DateRangeFormatter implements Formatter<DateRange> {
    @SuppressWarnings("unchecked")
    public static class DateRangeAnnotationFormatterFactory implements AnnotationFormatterFactory<DateTimeFormat> {
        DateTimeFormatAnnotationFormatterFactory mFactory = new DateTimeFormatAnnotationFormatterFactory();

        @Override public Set<Class<?>> getFieldTypes() {
            return new HashSet<>(Arrays.asList(DateRange.class));
        }

        @Override public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
            return new DateRangeFormatter(new Formatter<Date>() {
                private Printer<Date> mPrinter = ((Printer<Date>) mFactory.getPrinter(annotation, Date.class));

                @Override public Date parse(String text, Locale locale) throws ParseException {
                    return null;
                }

                @Override public String print(Date object, Locale locale) {
                    return mPrinter.print(object, locale);
                }
            });
        }

        @Override public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
            return new DateRangeFormatter(new Formatter<Date>() {
                private Parser<Date> mParser = ((Parser<Date>) mFactory.getParser(annotation, Date.class));

                @Override public Date parse(String text, Locale locale) throws ParseException {
                    return mParser.parse(text, locale);
                }

                @Override public String print(Date object, Locale locale) {
                    return null;
                }
            });
        }
    }

    Formatter<Date> mDateFormatter;

    public DateRangeFormatter(Formatter<Date> dateFormatter) {
        mDateFormatter = dateFormatter;
    }

    @Override public DateRange parse(String text, Locale locale) throws ParseException {
        String[] strings = text.split("-", 2);
        if (strings.length != 2) {
            throw new ParseException("Date format", 0);
        }

        return new DateRange(mDateFormatter.parse(strings[0].trim(), locale),
                mDateFormatter.parse(strings[1].trim(), locale));
    }

    @Override public String print(DateRange object, Locale locale) {
        return mDateFormatter.print(object.getFrom(), locale) + " - " + mDateFormatter.print(object.getTo(), locale);
    }
}
