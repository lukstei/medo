package lst.medo.controller;

import lst.medo.dao.AuthorDao;
import lst.medo.dao.MediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AjaxController {
    @Autowired AuthorDao mAuthorDao;
    @Autowired MediaDao mMediaDao;

    @RequestMapping(value = "/ajax", method = RequestMethod.GET)
    public List<?> ajax(@RequestParam(value = "type") String type,
                        @RequestParam(value = "q") String q) {
        switch (type) {
            case "author":
                return mAuthorDao.findByNameStartingWith(q);
            case "media":
                return mMediaDao.findByNameStartingWith(q);
        }

        throw new IllegalArgumentException("type");
    }

}
