package lst.medo.dao;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


/**
 * User dao
 */
public interface UserDao {
    /**
     * @return all users
     */
    Collection<? extends UserDetails> findAll();


    /**
     * @return the given user or null
     */
    UserDetails find(String username);
}
