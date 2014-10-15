package lst.medo.model;


import lst.medo.config.Util;

import javax.annotation.Nullable;
import java.time.LocalDate;

import static java.lang.String.format;

public class Article {
    @Nullable Integer id;
    String type;
    String text;
    @Nullable String author;
    LocalDate date;
    String media;

    public Article() {
    }

    public Article(@Nullable Integer id, String type, String text, @Nullable String author, LocalDate date, String media) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.author = author;
        this.date = date;
        this.media = media;
    }

    @Nullable public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Nullable public String getAuthor() {
        return author;
    }

    public void setAuthor(@Nullable String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;

        Article article = (Article) o;

        if (id != null ? !id.equals(article.id) : article.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override public String toString() {
        return format("%s %s am %s",
                getType(),
                getMedia() + (getAuthor() != null ? " (" + getAuthor() + ")" : ""),
                getDate().format(Util.SHORT_DATE));
    }
}
