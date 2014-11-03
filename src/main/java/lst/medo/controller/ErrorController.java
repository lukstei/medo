package lst.medo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class ErrorController implements org.springframework.boot.autoconfigure.web.ErrorController {
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    public String error(ModelMap model, HttpServletRequest req) {
        Throwable e = ((Throwable) req.getAttribute("javax.servlet.error.exception"));
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        if(e != null) {
            e.printStackTrace(pw);
        }

        model.addAttribute("uri", req.getRequestURI());
        model.addAttribute("error", e);
        model.addAttribute("stackTrace", sw.toString());
        return "error";
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}