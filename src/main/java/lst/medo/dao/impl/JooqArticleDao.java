package lst.medo.dao.impl;

import lst.medo.dao.ArticleDao;
import lst.medo.generated.tables.records.ArticleRecord;
import lst.medo.generated.tables.records.AuthorRecord;
import lst.medo.generated.tables.records.MediaRecord;
import lst.medo.model.Article;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.SelectConditionStep;

import javax.annotation.Nullable;
import java.util.List;

import static lst.medo.generated.Tables.*;

public class JooqArticleDao implements ArticleDao {
    DSLContext mContext;

    public JooqArticleDao(DSLContext context) {
        mContext = context;
    }

    @Override public Article save(Article article) {
        ArticleRecord articleRecord = null;
        if (article.getId() != null) {
            articleRecord = mContext.fetchOne(ARTICLE, ARTICLE.ID.eq(article.getId()));
        }

        if (articleRecord == null) {
            articleRecord = mContext.newRecord(ARTICLE);
        }

        int articleType = Util.ARTICLE_TYPES.getOrDefault(article.getType(), 0);
        @Nullable AuthorRecord author = null;

        if (article.getAuthor() != null) {
            author = mContext.fetchOne(AUTHOR, AUTHOR.NAME.eq(article.getAuthor()));
            if (author == null) {
                author = mContext.newRecord(AUTHOR);
                author.setName(article.getAuthor());
                author.store();
            }
        }

        MediaRecord media = mContext.fetchOne(MEDIA, MEDIA.NAME.eq(article.getMedia()));
        if (media == null) {
            media = mContext.newRecord(MEDIA);
            media.setName(article.getMedia());
            media.store();
        }

        articleRecord.setArticleDate(Util.toSqlDate(article.getDate()));
        articleRecord.setArticleType(articleType);
        articleRecord.setAuthor(author != null ? author.getId() : null);
        articleRecord.setMedia(media.getId());
        articleRecord.setTxt(article.getText());

        articleRecord.store();

        article.setId(articleRecord.getId());
        return article;
    }

    @Override public List<Article> find(Params params) {
        SelectConditionStep<Record> sql = mContext.select().from(ARTICLE)
                .leftOuterJoin(AUTHOR).on(ARTICLE.AUTHOR.eq(AUTHOR.ID))
                .join(MEDIA).on(ARTICLE.MEDIA.eq(MEDIA.ID))
                .join(ARTICLE_TYPE).on(ARTICLE.ARTICLE_TYPE.eq(ARTICLE_TYPE.ID))
                .where();

        if (params.getId() != null) {
            sql = sql.and(ARTICLE.ID.eq(params.getId()));
        }
        if (params.getText() != null) {
            sql = sql.and(ARTICLE.TXT.likeIgnoreCase("%" + params.getText() + "%"));
        }
        if (params.getAuthor() != null) {
            sql = sql.and(AUTHOR.NAME.likeIgnoreCase("%" + params.getAuthor() + "%"));
        }
        if (params.getMedia() != null) {
            sql = sql.and(MEDIA.NAME.likeIgnoreCase("%" + params.getMedia() + "%"));
        }
        if (params.getFrom() != null) {
            sql = sql.and(ARTICLE.ARTICLE_DATE.greaterOrEqual(Util.toSqlDate(params.getFrom())));
        }
        if (params.getTo() != null) {
            sql = sql.and(ARTICLE.ARTICLE_DATE.lessOrEqual(Util.toSqlDate(params.getTo())));
        }

        return sql.fetch(r ->
                new Article(
                        r.getValue(ARTICLE.ID),
                        r.getValue(ARTICLE_TYPE.NAME),
                        r.getValue(ARTICLE.TXT),
                        r.getValue(AUTHOR.NAME),
                        Util.fromSqlDate(r.getValue(ARTICLE.ARTICLE_DATE)),
                        r.getValue(MEDIA.NAME)));
    }
}
