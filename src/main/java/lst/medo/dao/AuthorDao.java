package lst.medo.dao;

import lst.medo.model.Author;

import java.util.List;

/**
 * Author dao
 */
public interface AuthorDao {
    /**
     * Finds an author which name starts with "name".
     * @param name starting string
     * @return list of found authors
     */
    List<Author> findByNameStartingWith(String name);
}
