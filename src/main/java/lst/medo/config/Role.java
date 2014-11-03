package lst.medo.config;

import java.util.Arrays;
import java.util.List;

public class Role {
    public static final String ROLE_ARTICLE_EDIT = "article.edit";
    public static final String ROLE_ARTICLE_CREATE = "article.create";

    public static final List<String> ROLES =
            Arrays.asList(
                    ROLE_ARTICLE_CREATE,
                    ROLE_ARTICLE_EDIT
            );
}
