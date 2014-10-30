package lst.medo.config;

import lst.medo.dao.ArticleDao;
import lst.medo.model.Article;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

public interface UrlCreator {
    static UrlCreator viewArticle(Article article) {
        return new DefaultUrlCreator(m -> "/articles/" + m.get("article"))
                .set("article", article.getId());
    }

    // /articles?text=&author=&media=&date=
    static UrlCreator searchArticles(ArticleDao.Params params) {
        return new DefaultUrlCreator(queryParams("/articles"))
                .set("text", params.getText())
                .set("author", params.getAuthor())
                .set("media", params.getMedia())
                .set("page", params.getPage().getPage());
    }

    class DefaultUrlCreator implements UrlCreator {
        Map<String, Object> mValues = new HashMap<>();
        Function<Map<String, String>, String> mCreator;

        public DefaultUrlCreator(Function<Map<String, String>, String> creator) {
            mCreator = creator;
        }

        @Override public String create() {
            return mCreator.apply(mValues.entrySet().stream()
                    .filter(e -> e.getValue() != null)
                    .collect(toMap(Map.Entry::getKey, e -> mapToString.apply(e.getValue()))));
        }

        @Override public UrlCreator with(String name, Object value) {
            if (!mValues.containsKey(name)) {
                throw new IllegalArgumentException("name");
            }

            return set(name, value);
        }

        public DefaultUrlCreator with(String name) {
            mValues.put(name, null);
            return this;
        }

        public DefaultUrlCreator set(String name, Object value) {
            mValues.remove(name);
            mValues.put(name, value);
            return this;
        }
    }

    String create();

    UrlCreator with(String name, Object value);

    default Function<Object, String> setting(String name) {
        return o -> with(name, mapToString.apply(o)).create();
    }

    static Function<Object, String> mapToString = o -> {
        if (o == null) {
            return "";
        }

        try {
            return URLEncoder.encode(String.valueOf(o), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    };

    static Function<Map<String, String>, String> queryParams(String path) {
        return m -> {
            StringBuilder builder = new StringBuilder(path);
            if (!m.isEmpty()) {
                builder.append("?");

                builder.append(m.entrySet()
                        .stream()
                        .map(e -> e.getKey() + "=" + e.getValue())
                        .collect(joining("&")));
            }

            return builder.toString();
        };
    }
}
