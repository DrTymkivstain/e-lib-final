package com.example.elibfinal.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "book_id", unique = true)
    @ToString.Exclude
    @JsonBackReference
    private Book book;
}
