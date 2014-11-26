package lst.medo.dao;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


public interface UserDao {
    /**
     * @return all users
     */
    Collection<? extends UserDetails> findAll();
}
