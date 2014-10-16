package lst.medo.dao;

import lst.medo.model.Article;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;

public interface ArticleDao {
    Article save(Article article);

    List<Article> find(Params params);
    @Nullable Article findById(int id);

    class Params {
        @Nullable Integer id;
        @Nullable String text;
        @Nullable String author;
        @Nullable String media;
        @Nullable LocalDate from;
        @Nullable LocalDate to;

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

        @Nullable public LocalDate getFrom() {
            return from;
        }

        public void setFrom(@Nullable LocalDate from) {
            this.from = from;
        }

        @Nullable public LocalDate getTo() {
            return to;
        }

        public void setTo(@Nullable LocalDate to) {
            this.to = to;
        }
    }
}

