package com.example.alazani.repo;

import com.example.alazani.entity.BlackList;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BlackListRepository extends CrudRepository<BlackList, String> {
    boolean existsByBorrowerId(String borrowerId);

    List<BlackList> findAll();

    List<BlackList> findAllByBorrowerId(String borrowerId);
}
