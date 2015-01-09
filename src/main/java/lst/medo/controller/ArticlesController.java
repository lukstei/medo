package lst.medo.controller;

import lst.medo.config.Role;
import lst.medo.config.UrlCreator;
import lst.medo.config.Util;
import lst.medo.dao.ArticleDao;
import lst.medo.model.Article;
import lst.medo.model.DateRange;
import lst.medo.model.PageInfo;
import lst.medo.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class ArticlesController {
    @Autowired ArticleDao mArticleDao;
    @Autowired HttpServletRequest mRequest;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index() {
        return "article/search";
    }

    @RequestMapping(value = "/articles", method = RequestMethod.GET)
    public String search(ModelMap model,
                         @RequestParam(value = "text", required = false) String text,
                         @RequestParam(value = "author", required = false) String author,
                         @RequestParam(value = "media", required = false) String media,
                         @RequestParam(value = "date", required = false)
                         @DateTimeFormat(pattern = "dd.MM.yyyy") DateRange dateRange,
                         @RequestParam(value = "page", required = false) Optional<Integer> pageO) {
        ArticleDao.Params params = new ArticleDao.Params();
        params.setText(text);
        params.setAuthor(author);
        params.setMedia(media);
        params.setPage(params.getPage().withPageNum(pageO.orElse(1)));

        if (dateRange != null) {
            params.setFrom(new Date(dateRange.getFrom().getTime()));
            params.setTo(new Date(dateRange.getTo().getTime()));
        }

        String s = Stream.of(author, media, text).filter(t -> !Util.isEmpty(t)).collect(Collectors.joining(" - "));

        Result<Article> result = mArticleDao.find(params);
        model.addAttribute("result", result.getItems());

        PageInfo pageInfo = new PageInfo(params.getPage(), result.getCount());
        model.addAttribute("pageToUrl", UrlCreator.urlSettingQueryParam(mRequest, "page"));

        model.addAttribute("page", pageInfo);
        model.addAttribute("search", s);

        return "article/search";
    }

    @Secured(Role.ROLE_ARTICLE_CREATE)
    @RequestMapping(value = "/articles/new", method = RequestMethod.GET)
    public String createNew(ModelMap model) {
        model.addAttribute("isNew", true);
        Article article = new Article();
        article.setDate(LocalDate.now());
        model.addAttribute("article", article);
        return "article/form";
    }

    @Secured(Role.ROLE_ARTICLE_EDIT)
    @RequestMapping(value = "/articles/{id}/edit", method = RequestMethod.GET)
    public String edit(ModelMap model, @PathVariable int id) {
        model.addAttribute("isNew", false);
        model.addAttribute("article", mArticleDao.findById(id));
        return "article/form";
    }

    @Secured(Role.ROLE_ARTICLE_CREATE)
    @RequestMapping(value = "/articles", method = RequestMethod.POST)
    public String create(ModelMap model,
                         @ModelAttribute("article") @Valid Article article,
                         BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            return "article/form";
        }

        mArticleDao.save(article);

        return "redirect:/articles/" + article.getId();
    }

    @Secured(Role.ROLE_ARTICLE_EDIT)
    @RequestMapping(value = "/articles/{id}", method = RequestMethod.POST)
    public String update(ModelMap model,
                         @PathVariable int id,
                         @ModelAttribute("article") @Valid Article article,
                         BindingResult result) {
        if (result.hasErrors()) {
            model.addAttribute("isNew", true);
            return "article/form";
        }

        article.setId(id);
        mArticleDao.save(article);

        return "redirect:/articles/" + article.getId();
    }

    @Secured(Role.ROLE_ARTICLE_EDIT)
    @RequestMapping(value = "/articles/{id}/delete", method = RequestMethod.POST)
    public String delete(@PathVariable int id,
                         RedirectAttributes redirectAttributes) {
        mArticleDao.delete(id);

        redirectAttributes.addFlashAttribute("message", "Artikel gelöscht");

        return "redirect:/";
    }

    @RequestMapping(value = "/articles/{id}", method = RequestMethod.GET)
    public String view(ModelMap model, @PathVariable int id) {
        model.addAttribute("article", mArticleDao.findById(id));
        return "article/view";
    }
}