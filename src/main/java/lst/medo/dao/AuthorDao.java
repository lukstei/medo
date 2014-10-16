package lst.medo.dao;

import lst.medo.model.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findByNameStartingWith(String name);
}
