package com.example.elibfinal.repository;

import com.example.elibfinal.models.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);

    List<Book> findAllByAvailableIsTrue(Pageable pageable);

}
