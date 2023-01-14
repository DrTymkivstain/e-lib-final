package com.example.elibfinal.repository;

import com.example.elibfinal.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
