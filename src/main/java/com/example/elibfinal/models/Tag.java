package com.example.elibfinal.models;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "tag_name")
    private String tagName;
    @ManyToMany
    @Column(name = "books")
    private Set<Book> books;


}
