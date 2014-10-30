package lst.medo.dao.impl;

import lst.medo.dao.ArticleDao;
import lst.medo.generated.tables.records.ArticleRecord;
import lst.medo.generated.tables.records.AuthorRecord;
import lst.medo.generated.tables.records.MediaRecord;
import lst.medo.model.Article;
import lst.medo.model.Result;
import org.jooq.*;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;

import static lst.medo.dao.impl.MatchesFulltextCondition.*;
import static lst.medo.generated.Tables.*;
import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.val;

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
            }

            author.setName(article.getAuthor());
            author.store();
        }

        MediaRecord media = mContext.fetchOne(MEDIA, MEDIA.NAME.eq(article.getMedia()));
        if (media == null) {
            media = mContext.newRecord(MEDIA);
        }
        media.setName(article.getMedia());
        media.store();

        articleRecord.setArticleDate(Util.toSqlDate(article.getDate()));
        articleRecord.setArticleType(articleType);
        articleRecord.setAuthor(author != null ? author.getId() : null);
        articleRecord.setMedia(media.getId());
        articleRecord.setTxt(article.getText());

        articleRecord.store();

        article.setId(articleRecord.getId());
        return article;
    }

    @Override public Result<Article> find(Params params) {
        boolean isFulltextSearch = params.getText() != null;

        SelectSelectStep<?> select = mContext
                .select(ARTICLE.ID, ARTICLE_TYPE.NAME, AUTHOR.NAME, ARTICLE.ARTICLE_DATE, MEDIA.NAME);

        Field<String> tsQuery = plainToTsQuery(val(params.getText()));
        Field<String> tsHeadline = tsHeadline(ARTICLE.TXT, tsQuery);

        if (isFulltextSearch) {
            select = select.select(tsHeadline);
        }

        SelectConditionStep<?> where = select
                .from(ARTICLE)
                .leftOuterJoin(AUTHOR).on(ARTICLE.AUTHOR.eq(AUTHOR.ID))
                .join(MEDIA).on(ARTICLE.MEDIA.eq(MEDIA.ID))
                .join(ARTICLE_TYPE).on(ARTICLE.ARTICLE_TYPE.eq(ARTICLE_TYPE.ID))
                .where();

        if (params.getId() != null) {
            where = where.and(ARTICLE.ID.eq(params.getId()));
        }
        if (params.getText() != null) {
            where = where.and(matchesFulltext(ARTICLE.FULLTEXT_INDEX, tsQuery));
        }
        if (params.getAuthor() != null) {
            where = where.and(orQuery(params.getAuthor(), t -> AUTHOR.NAME.likeIgnoreCase("%" + t + "%")));
        }
        if (params.getMedia() != null) {
            where = where.and(orQuery(params.getMedia(), t -> MEDIA.NAME.likeIgnoreCase("%" + t + "%")));
        }
        if (params.getFrom() != null) {
            where = where.and(ARTICLE.ARTICLE_DATE.greaterOrEqual(params.getFrom()));
        }
        if (params.getTo() != null) {
            where = where.and(ARTICLE.ARTICLE_DATE.lessOrEqual(params.getTo()));
        }

        int count = where.fetchCount();
        List<Article> articles = where
                //.groupBy(ARTICLE.ID, ARTICLE_TYPE.NAME, AUTHOR.NAME, ARTICLE.ARTICLE_DATE, MEDIA.NAME)
                .orderBy(ARTICLE.ARTICLE_DATE.desc())
                .limit(params.getPage().getItemsPerPage())
                .offset(params.getPage().getOffset())
                .fetch(r ->
                    new Article(
                            r.getValue(ARTICLE.ID),
                            r.getValue(ARTICLE_TYPE.NAME),
                            isFulltextSearch ? r.getValue(tsHeadline) : null,
                            r.getValue(AUTHOR.NAME),
                            Util.fromSqlDate(r.getValue(ARTICLE.ARTICLE_DATE)),
                            r.getValue(MEDIA.NAME))
                );

        return new Result<>(articles, count);
    }

    @Nullable @Override public Article findById(int id) {
        return mContext.select(ARTICLE.ID, ARTICLE.TXT, ARTICLE_TYPE.NAME, AUTHOR.NAME, ARTICLE.ARTICLE_DATE, MEDIA.NAME)
                .from(ARTICLE)
                .leftOuterJoin(AUTHOR).on(ARTICLE.AUTHOR.eq(AUTHOR.ID))
                .join(MEDIA).on(ARTICLE.MEDIA.eq(MEDIA.ID))
                .join(ARTICLE_TYPE).on(ARTICLE.ARTICLE_TYPE.eq(ARTICLE_TYPE.ID))
                .where(ARTICLE.ID.eq(id))
                .fetchOne()
                .map(r ->
                        new Article(
                                r.getValue(ARTICLE.ID),
                                r.getValue(ARTICLE_TYPE.NAME),
                                r.getValue(ARTICLE.TXT),
                                r.getValue(AUTHOR.NAME),
                                Util.fromSqlDate(r.getValue(ARTICLE.ARTICLE_DATE)),
                                r.getValue(MEDIA.NAME)));
    }

    @Override public void delete(int id) {
        mContext.delete(ARTICLE).where(ARTICLE.ID.eq(id));
    }

    Condition orQuery(String query, Function<String, Condition> f) {
        String[] strings = Pattern.compile(" ODER ").split(query);
        Condition condition = null;
        for (String string : strings) {
            Condition or = f.apply(string.trim());
            condition = condition == null ? or : condition.or(or);
        }

        return condition;
    }
}
