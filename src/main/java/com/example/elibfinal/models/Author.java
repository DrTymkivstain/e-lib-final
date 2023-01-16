package com.example.elibfinal.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "author_name")
    private String name;
    @Column(name = "books")
    @ManyToMany
    private Set<Book> books;

}
