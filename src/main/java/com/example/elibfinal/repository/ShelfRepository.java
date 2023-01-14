package com.example.elibfinal.repository;

import com.example.elibfinal.models.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Optional<Shelf> findByBookIsNull();
}
