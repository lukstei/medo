package lst.medo.dao.impl;

import lst.medo.dao.MediaDao;
import lst.medo.model.Media;
import org.jooq.DSLContext;

import java.util.List;

import static lst.medo.generated.Tables.MEDIA;


public class JooqMediaDao implements MediaDao {
    DSLContext mContext;

    public JooqMediaDao(DSLContext context) {
        mContext = context;
    }

    @Override public List<Media> findByNameStartingWith(String name) {
        return mContext.select(MEDIA.ID, MEDIA.NAME)
                .from(MEDIA)
                .where(MEDIA.NAME.likeIgnoreCase(name + "%").or(MEDIA.NAME.likeIgnoreCase("% " + name + "%")))
                .limit(10)
                .fetch(r -> new Media(
                        r.getValue(MEDIA.ID),
                        r.getValue(MEDIA.NAME)
                ));
    }
}
