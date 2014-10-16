package lst.medo.dao.impl;

import org.jooq.Context;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.impl.CustomCondition;
import org.jooq.impl.DSL;

public class MatchesFulltextCondition extends CustomCondition {
    public static Field<String> plainToTsQuery(Field<String> query) {
        return DSL.function("plainto_tsquery", String.class, query);
    }

    public static Field<String> tsHeadline(Field<String> body, Field<String> tsQuery) {
        return DSL.function("ts_headline", String.class, body, tsQuery);
    }

    public static MatchesFulltextCondition matchesFulltext(Field<?> search, Field<String> pattern) {
        return new MatchesFulltextCondition(search, pattern);
    }

    private final Field<?> search;
    private final Field<String> pattern;

    MatchesFulltextCondition(Field<?> search, Field<String> pattern) {
        this.search = search;
        this.pattern = pattern;
    }

    @Override
    public final void accept(Context<?> ctx) {
        assert ctx.configuration().dialect().family() == SQLDialect.POSTGRES;

        ctx.visit(search)
                .sql(" ")
                .keyword("@@")
                .sql(" ")
                .visit(pattern);
    }
}
