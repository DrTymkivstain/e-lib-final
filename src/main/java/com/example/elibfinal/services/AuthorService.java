package com.example.elibfinal.services;

import com.example.elibfinal.exception.CustomException;
import com.example.elibfinal.models.Author;
import com.example.elibfinal.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Set<Author> getAuthorsFromStringArray(String[] authors) {
        return Arrays.stream(authors)
                .map(a -> authorRepository.findByName(a).orElseThrow(() -> new CustomException("author not found!")))
                .collect(Collectors.toSet());
    }

    public String[] getStringArrayFromAuthors(Set<Author> authors) {
        return (String[]) authors.stream()
                .map(a -> authorRepository.findById(a.getId()).orElseThrow(() -> new CustomException("author not found!")).getName())
                .toArray();
    }

    public List<Author> getAllAuthors() {
    return authorRepository.findAll();
    }
}
