package lst.medo.config;

import lst.medo.model.Article;

public class UrlCreator {
    public String viewArticle(Article article) {
        return "/articles/" + article.getId();
    }
}
