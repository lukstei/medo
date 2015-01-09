package lst.medo.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Controller
public class ControllerAdvices {
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // treat empty parameter strings as null
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}