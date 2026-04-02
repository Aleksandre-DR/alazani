package com.example.alazani.repo;

import com.example.alazani.entity.Book;
import com.example.alazani.entity.BookBorrowed;
import com.example.alazani.entity.Borrower;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookBorrowedRepository extends CrudRepository<BookBorrowed, Long> {

    boolean existsByBorrower(Borrower borrower);

    List<BookBorrowed> findByBorrower(Borrower borrowerId);

    Optional<BookBorrowed> findByBook(Book book);

    @NullMarked
    List<BookBorrowed> findAll();

    @Transactional
    void deleteByBook(Book book);
}
