package lst.medo;

import lst.medo.dao.ArticleDao;
import lst.medo.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ArticleController {
    @Autowired ArticleDao mArticleDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "index";
    }

    @RequestMapping(value = "/view/{id}{slug}", method = RequestMethod.GET)
    public String view(ModelMap model, @PathVariable int id, @PathVariable String slug) {
        List<Article> articles = mArticleDao.find(ArticleDao.Params.byId(id));
        if(articles.isEmpty()) {
            throw new RuntimeException("not found");
        }

        model.addAttribute("article", articles.get(0));
        return "article/view";
    }
}