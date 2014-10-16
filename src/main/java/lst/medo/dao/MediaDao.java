package lst.medo.dao;

import lst.medo.model.Media;

import java.util.List;

public interface MediaDao {
    List<Media> findByNameStartingWith(String name);
}
