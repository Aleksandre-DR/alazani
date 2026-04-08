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
        return bookRepo.findByAuthor(author);
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

        return bookRepo.findByAuthor(author).stream()
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public long numberOfBook(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new BookNotInStoreException();
        }
        return bookRepo.countByName(bookName);
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
        return bookRepo.findByAuthor(author).stream()
                .filter(Book::isAvailable)
                .map(Book::getName)
                .distinct()
                .toList();
    }

    public boolean isBookInStore(String bookName) {
        return bookRepo.existsByName(bookName);
    }

    public boolean isBookAvailable(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new BookNotInStoreException();
        }
        return bookRepo.existsByNameAndIsAvailableTrue(bookName);
    }

    public boolean isAuthorInStore(String author) {
        return bookRepo.existsByAuthor(author);
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

        return bookRepo.findByName(bookName).stream()
                .filter(Book::isAvailable)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("book not free"));
    }

    public void fillTable() {
        String bookFilePath = "src/main/resources/books.txt";

        try (var br = new BufferedReader(new FileReader(bookFilePath))) {
            String oneLine;
            Book book;

            while ((oneLine = br.readLine()) != null) {
                book = bookMaker(oneLine);
                if (bookRepo.existsById(book.getId())) continue;
                bookRepo.save(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Book bookMaker(String bookAsString){
        String[] splitLine = bookAsString.split("/");
        String id = splitLine[0];
        String name = splitLine[1].toLowerCase();       // book name in lower case
        String author = splitLine[2].toLowerCase();     // author in lower case

        return new Book(id, name, author);
    }

    public void deleteFromTable(String bookId) {
        if (!bookRepo.existsById(bookId)) {
            throw new BookNotInStoreException();
        }
        bookRepo.deleteById(bookId);
    }

    public void deleteAllFromTable() {
        bookRepo.deleteAll();
    }
}
