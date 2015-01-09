package lst.medo.dao;

import lst.medo.model.Media;

import java.util.List;

/**
 * Media dao
 */
public interface MediaDao {
    /**
     * Finds media which name starts with "name".
     * @param name starting string
     * @return found media
     */
    List<Media> findByNameStartingWith(String name);
}
