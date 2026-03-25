package com.example.alazani.repo;

import com.example.alazani.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
    Optional<Book> findByNameIgnoreCase(String name);

    Optional<Book> findByNameIgnoreCaseAndIsAvailableEqualsTrue(String name);

    Iterable<Book> findByAuthorIgnoreCase(String author);
}
