package com.example.alazani.repo;

import com.example.alazani.entity.Borrower;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BorrowerRepository extends CrudRepository<Borrower, String> {

}
