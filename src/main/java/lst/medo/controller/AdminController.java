package lst.medo.controller;

import lst.medo.config.Role;
import lst.medo.config.Util;
import lst.medo.dao.UserDao;
import lst.medo.model.SimpleUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Admin controller
 */
@Controller
public class AdminController {
    @Autowired PasswordEncoder mPasswordEncoder;
    @Autowired UserDetailsManager mUserDetailsManager;
    @Autowired UserDao mUserDao;

    /**
     * User management action
     */
    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users", method = RequestMethod.GET)
    public String users(ModelMap model) {
        model.put("roles", Role.ROLES);
        model.put("role_descriptions", Role.ROLE_DESCRIPTIONS);
        model.put("users", mUserDao.findAll());
        return "admin/userlist";
    }

    /**
     * Create user action
     */
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

    /**
     * Delete user action
     */
    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users/{name}/delete", method = RequestMethod.POST)
    public String deleteUser(ModelMap model,
                             @PathVariable("name") String username,
                             RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("success", "Benutzer gelöscht");
        mUserDetailsManager.deleteUser(username);
        return "redirect:/admin/users";
    }

    /**
     * Update user action
     */
    @Secured(value = Role.ROLE_ADMIN)
    @RequestMapping(value = "/admin/users/{name}", method = RequestMethod.POST)
    public String updateUser(ModelMap model,
                             @PathVariable("name") String username,
                             @RequestParam("roles") String roles,
                             RedirectAttributes redirectAttributes) {

        if (mUserDetailsManager.userExists(username) && !username.equals("admin")) {
            String[] newRoles = Util.isEmpty(roles) ? new String[0] : roles.split(",");

            UserDetails userDetails = mUserDao.find(username);

            List<GrantedAuthority> authorities = Arrays.asList(newRoles).stream()
                    .map(String::trim)
                    .filter(Role.ROLES::contains)
                    .map(SimpleGrantedAuthority::new)
                    .collect(toList());

            mUserDetailsManager.updateUser(new UserDetailsProxy(userDetails) {
                @Override public Collection<? extends GrantedAuthority> getAuthorities() {
                    return authorities;
                }
            });

            redirectAttributes.addFlashAttribute("success", "Änderungen gespeichert");
        }

        return "redirect:/admin/users";
    }

    static class UserDetailsProxy implements UserDetails {
        UserDetails mUserDetails;

        public UserDetailsProxy(UserDetails userDetails) {
            mUserDetails = userDetails;
        }

        @Override public Collection<? extends GrantedAuthority> getAuthorities() {
            return mUserDetails.getAuthorities();
        }

        @Override public String getPassword() {
            return mUserDetails.getPassword();
        }

        @Override public String getUsername() {
            return mUserDetails.getUsername();
        }

        @Override public boolean isAccountNonExpired() {
            return mUserDetails.isAccountNonExpired();
        }

        @Override public boolean isAccountNonLocked() {
            return mUserDetails.isAccountNonLocked();
        }

        @Override public boolean isCredentialsNonExpired() {
            return mUserDetails.isCredentialsNonExpired();
        }

        @Override public boolean isEnabled() {
            return mUserDetails.isEnabled();
        }
    }
}
