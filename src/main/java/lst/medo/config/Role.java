package lst.medo.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

/**
 * Role system
 */
public class Role {
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_ARTICLE_EDIT = "article.edit";
    public static final String ROLE_ARTICLE_CREATE = "article.create";

    /***
     * Descriptions for the roles (used in the web ui)
     */
    public static final Map<String, String> ROLE_DESCRIPTIONS = new TreeMap<String, String>() {{
        put(ROLE_ADMIN, "Benutzer anlegen, editieren und löschen");
        put(ROLE_ARTICLE_CREATE, "Artikel anlegen");
        put(ROLE_ARTICLE_EDIT, "Artikel editieren und löschen");
    }};

    public static final Collection<String> ROLES = ROLE_DESCRIPTIONS.keySet();
}
