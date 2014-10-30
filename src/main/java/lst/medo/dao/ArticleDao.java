package lst.medo.dao;

import lst.medo.model.Article;
import lst.medo.model.Page;
import lst.medo.model.Result;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

public interface ArticleDao {
    Article save(Article article);

    Result<Article> find(Params params);

    @Nullable Article findById(int id);

    void delete(int id);

    class Params {
        @Nullable Integer id;
        @Nullable String text;
        @Nullable String author;
        @Nullable String media;
        @Nullable Date from;
        @Nullable Date to;
        Page page = new Page(1, 30);

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }

        @Nullable public Integer getId() {
            return id;
        }

        public void setId(@Nullable Integer id) {
            this.id = id;
        }

        @Nullable public String getText() {
            return text;
        }

        public void setText(@Nullable String text) {
            this.text = text;
        }

        @Nullable public String getAuthor() {
            return author;
        }

        public void setAuthor(@Nullable String author) {
            this.author = author;
        }

        @Nullable public String getMedia() {
            return media;
        }

        public void setMedia(@Nullable String media) {
            this.media = media;
        }

        @Nullable public Date getFrom() {
            return from;
        }

        public void setFrom(@Nullable Date from) {
            this.from = from;
        }

        @Nullable public Date getTo() {
            return to;
        }

        public void setTo(@Nullable Date to) {
            this.to = to;
        }
    }
}

