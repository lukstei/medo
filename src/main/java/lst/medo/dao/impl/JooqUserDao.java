package lst.medo.dao.impl;

import lst.medo.dao.UserDao;
import lst.medo.model.SimpleUserDetails;
import org.jooq.DSLContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static lst.medo.generated.Tables.AUTHORITIES;
import static lst.medo.generated.Tables.USERS;

public class JooqUserDao implements UserDao {
    DSLContext mContext;

    public JooqUserDao(DSLContext context) {
        mContext = context;
    }

    @Override public Collection<? extends UserDetails> findAll() {
        Map<String, SimpleUserDetails> users = new HashMap<>();

        mContext.select(USERS.USERNAME, AUTHORITIES.AUTHORITY).from(USERS)
                .leftOuterJoin(AUTHORITIES).on(USERS.USERNAME.eq(AUTHORITIES.USERNAME))
                .fetch(u -> {
                    String username = u.getValue(USERS.USERNAME);
                    SimpleUserDetails details = users.computeIfAbsent(username, x -> new SimpleUserDetails(username));
                    String role = u.getValue(AUTHORITIES.AUTHORITY);
                    if (role != null) {
                        details.getAuthorities().add(new SimpleGrantedAuthority(role));
                    }
                    return details;
                });

        return users.values();
    }
}
