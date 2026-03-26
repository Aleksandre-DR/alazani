package com.example.alazani.service;

import com.example.alazani.entity.Book;
import com.example.alazani.exception.ResourceNotFoundException;
import com.example.alazani.repo.BookRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BookService {
    private BookRepository bookRepo;

    private BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public boolean isBookInStore(String bookName) {
        return bookRepo.findByNameIgnoreCase(bookName).isPresent();
    }

    public boolean isBookAvailable(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new ResourceNotFoundException("no such book in store");
        }
        return bookRepo.existsByNameIgnoreCaseAndIsAvailableTrue(bookName);
    }

    public long numberOfBook(String bookName) {
        if (!isBookInStore(bookName)) {
            throw new ResourceNotFoundException("no such book in store");
        }
        return bookRepo.countByNameIgnoreCase(bookName);
    }

    public boolean isAuthorInStore(String author) {
        return bookRepo.existsByAuthorIgnoreCase(author);
    }

    public List<Book> booksByAuthor(String author) {
        if (!isAuthorInStore(author)) {
            throw new ResourceNotFoundException("no such outhor in store");
        }

        List<Book> books = new ArrayList<>();
        Iterable<Book> bookIterable = bookRepo.findByAuthorIgnoreCase(author);
        bookIterable.forEach(books::add);
        return books;
    }

    public void fillDatabase() {
        String bookFilePath = "src/main/resources/books.txt";

        try (var br = new BufferedReader(new FileReader(bookFilePath))) {
            String oneLine;

            while ((oneLine = br.readLine()) != null) {
                String[] splittedLine = oneLine.split("/");
                String id = splittedLine[0];
                String name = splittedLine[1];
                String author = splittedLine[2];

                if (bookRepo.existsById(id)) continue;
                bookRepo.save(new Book(id, name, author));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
