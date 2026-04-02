package com.example.alazani.repo;

import com.example.alazani.entity.Book;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {

    boolean existsByNameIgnoreCase(String name);

    List<Book> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCaseAndIsAvailableTrue(String name);

    List<Book> findByAuthorIgnoreCase(String author);

    long countByNameIgnoreCase(String name);

    boolean existsByAuthorIgnoreCase(String author);

    @NullMarked
    List<Book> findAll();

    @Modifying
    @Query("update Book set isAvailable = ?2 where id = ?1")
    void setAvailabilityOf(String bookId, boolean isAvailable);
}
