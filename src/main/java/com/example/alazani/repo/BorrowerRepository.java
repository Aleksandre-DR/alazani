package com.example.alazani.repo;

import com.example.alazani.entity.Borrower;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowerRepository extends CrudRepository<Borrower, String> {
    @NullMarked
    List<Borrower> findAll();
}
