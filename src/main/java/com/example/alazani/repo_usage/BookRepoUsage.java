package com.example.alazani.repo_usage;

import com.example.alazani.entity.Book;
import com.example.alazani.exception.AuthorNotInStoreException;
import com.example.alazani.exception.BookNotInStoreException;
import com.example.alazani.repo.BookRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BookRepoUsage {
    private BookRepository bookRepo;

    private BookRepoUsage(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public boolean isBookInStore(String bookName) {
        return bookRepo.findByNameIgnoreCase(bookName).isPresent();
    }

    public boolean isBookAvailable(String bookName) throws Exception {
        if (!isBookInStore(bookName)) throw new BookNotInStoreException();
        return bookRepo.findByNameIgnoreCaseAndIsAvailableEqualsTrue(bookName).isPresent();
    }

    public boolean isAuthorInStore(String authorName) {
        return bookRepo.findByAuthorIgnoreCase(authorName)
                .iterator()
                .hasNext();
    }

    public List<Book> booksByAuthor(String authorName) throws AuthorNotInStoreException {
        if (!isAuthorInStore(authorName)) throw new AuthorNotInStoreException();
        Iterable<Book> bookIterable = bookRepo.findByAuthorIgnoreCase(authorName);

        return StreamSupport.stream(bookIterable.spliterator(), false).toList();
    }
}
