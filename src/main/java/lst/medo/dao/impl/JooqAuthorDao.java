package lst.medo.dao.impl;

import lst.medo.dao.AuthorDao;
import lst.medo.model.Author;
import org.jooq.DSLContext;

import java.util.List;

import static lst.medo.generated.Tables.AUTHOR;


public class JooqAuthorDao implements AuthorDao {
    DSLContext mContext;

    public JooqAuthorDao(DSLContext context) {
        mContext = context;
    }

    @Override public List<Author> findByNameStartingWith(String name) {
        return mContext.select(AUTHOR.ID, AUTHOR.NAME)
                .from(AUTHOR)
                .where(AUTHOR.NAME.likeIgnoreCase(name + "%").or(AUTHOR.NAME.likeIgnoreCase("% " + name + "%")))
                        .limit(10)
                        .fetch(r -> new Author(
                                r.getValue(AUTHOR.ID),
                                r.getValue(AUTHOR.NAME)
                        ));
    }
}
