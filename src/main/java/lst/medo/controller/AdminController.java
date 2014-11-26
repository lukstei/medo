package lst.medo.controller;

import lst.medo.config.Role;
import lst.medo.config.Util;
import lst.medo.dao.UserDao;
import lst.medo.model.SimpleUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {
    @Autowired PasswordEncoder mPasswordEncoder;
    @Autowired UserDetailsManager mUserDetailsManager;
    @Autowired UserDao mUserDao;

    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String users(ModelMap model) {
        model.put("roles", Role.ROLES);
        model.put("role_descriptions", Role.ROLE_DESCRIPTIONS);
        model.put("users", mUserDao.findAll());
        return "admin/userlist";
    }

    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users", method = RequestMethod.POST)
    public String createUser(ModelMap model,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             @RequestParam("password2") String password2,
                             RedirectAttributes redirectAttributes) {
        if (Util.isEmpty(username) || Util.isEmpty(password) || Util.isEmpty(password2)) {
            redirectAttributes.addFlashAttribute("error", "Bitte alle Felder ausfüllen");
        } else if (mUserDetailsManager.userExists(username)) {
            redirectAttributes.addFlashAttribute("error", "User existiert bereits");
        } else if (!password.equals(password2)) {
            redirectAttributes.addFlashAttribute("error", "Passwörter stimmen nicht überein");
        } else {
            SimpleUserDetails userDetails = new SimpleUserDetails(username);
            userDetails.setPassword(mPasswordEncoder.encode(password));

            mUserDetailsManager.createUser(userDetails);
        }

        return "redirect:/admin/users";
    }

    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users/{name}/delete", method = RequestMethod.POST)
    public String createUser(ModelMap model,
                             @PathVariable("name") String username,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Benutzer gelöscht");
        mUserDetailsManager.deleteUser(username);
        return "redirect:/admin/users";
    }
}
