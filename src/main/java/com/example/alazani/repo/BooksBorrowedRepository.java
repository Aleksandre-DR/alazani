package com.example.alazani.repo;

import com.example.alazani.entity.BooksBorrowed;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksBorrowedRepository extends CrudRepository<BooksBorrowed, Long> {
}
