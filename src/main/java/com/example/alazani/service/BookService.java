package com.example.alazani.service;

import com.example.alazani.entity.Book;
import com.example.alazani.exception.AuthorNotInStoreException;
import com.example.alazani.exception.BookNotInStoreException;
import com.example.alazani.repo.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;


@Service
public class BookService {
    private final BookRepository bookRepo;

    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> findAll() {
        return bookRepo.findAll();
    }

    public List<Book> findAllBy(String author) {
        if (!isAuthorInStore(author)) {
            throw new AuthorNotInStoreException();
        }
        return bookRepo.findByAuthorIgnoreCase(author);
    }

    public List<String> findDistincts() {
        return bookRepo.findAll().stream()
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public List<String> findDistinctsBy(String author) {
        if (!isAuthorInStore(author)) {
            throw new AuthorNotInStoreException();
        }

        return bookRepo.findByAuthorIgnoreCase(author).stream()
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public long numberOfBook(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new BookNotInStoreException();
        }
        return bookRepo.countByNameIgnoreCase(bookName);
    }

    public List<String> findDistinctAvailables() {
        return bookRepo.findAll().stream()
                .filter(Book::isAvailable)
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public List<String> findDistinctAvailablesBy(String author) {
        if (!isAuthorInStore(author)) {
            throw new AuthorNotInStoreException();
        }
        return bookRepo.findByAuthorIgnoreCase(author).stream()
                .filter(Book::isAvailable)
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public boolean isBookInStore(String bookName) {
        return bookRepo.existsByNameIgnoreCase(bookName);
    }

    public boolean isBookAvailable(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new BookNotInStoreException();
        }
        return bookRepo.existsByNameIgnoreCaseAndIsAvailableTrue(bookName);
    }

    public boolean isAuthorInStore(String author) {
        return bookRepo.existsByAuthorIgnoreCase(author);
    }

    public Book findById(String bookId) {
        return bookRepo.findById(bookId)
                .orElseThrow(BookNotInStoreException::new);
    }

    @Transactional           // because this is update-delete query call
    public void setAvailabilityOf(String bookId, boolean isAvailable) {
        bookRepo.setAvailabilityOf(bookId, isAvailable);
    }

    public Book findAvailableByName(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new BookNotInStoreException();
        }

        return bookRepo.findByNameIgnoreCase(bookName).stream()
                .filter(Book::isAvailable)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("book not free"));
    }

    public void fillTable() {
        String bookFilePath = "src/main/resources/books.txt";

        try (var br = new BufferedReader(new FileReader(bookFilePath))) {
            String oneLine;

            while ((oneLine = br.readLine()) != null) {
                String[] splitLine = oneLine.split("/");
                String id = splitLine[0];
                String name = splitLine[1];
                String author = splitLine[2];

                if (bookRepo.existsById(id)) continue;
                bookRepo.save(new Book(id, name, author));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        bookRepo.deleteAll();
    }
}
