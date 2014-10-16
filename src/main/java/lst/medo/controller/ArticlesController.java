package lst.medo.controller;

import lst.medo.config.Util;
import lst.medo.dao.ArticleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ArticlesController {
    @Autowired ArticleDao mArticleDao;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return "article/search";
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String search(ModelMap model,
                         @RequestParam(value = "text", required = false) String text,
                         @RequestParam(value = "author", required = false) String author,
                         @RequestParam(value = "media", required = false) String media,
                         @RequestParam(value = "type", required = false) String type) {
        ArticleDao.Params params = new ArticleDao.Params();
        params.setText(clean(text));
        params.setAuthor(clean(author));
        params.setMedia(clean(media));

        String s = Stream.of(author, media, text).filter(t -> !Util.isEmpty(t)).collect(Collectors.joining(" - "));

        model.addAttribute("result", mArticleDao.find(params));
        model.addAttribute("search", s);

        return "article/search";
    }

    private String clean(String text) {
        if (Util.isEmpty(text)) {
            return null;
        }
        return text.trim();
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public String view(ModelMap model, @PathVariable int id) {
        model.addAttribute("article", mArticleDao.findById(id));
        return "article/view";
    }
}