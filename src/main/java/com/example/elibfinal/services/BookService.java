package com.example.elibfinal.services;

import com.example.elibfinal.DTO.BookDTO;
import com.example.elibfinal.exception.CustomException;
import com.example.elibfinal.models.Book;
import com.example.elibfinal.models.Shelf;
import com.example.elibfinal.repository.BookRepository;
import com.example.elibfinal.repository.ShelfRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {
    private final TagService tagService;
    private final AuthorService authorService;
    private final ShelfRepository shelfRepository;
    private final BookRepository bookRepository;

    public BookService(TagService tagService,
                       AuthorService authorService,
                       ShelfRepository shelfRepository,
                       BookRepository bookRepository) {
        this.tagService = tagService;
        this.authorService = authorService;
        this.shelfRepository = shelfRepository;
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public BookDTO getBookDTOFromBook(Book book) {
        return BookDTO.builder()
                .name(book.getName())
                .tags(tagService.getStringArrayFromTags(book.getTags()))
                .authors(authorService.getStringArrayFromAuthors(book.getAuthors()))
                .build();

    }

    public void saveNewBookFromClient(BookDTO bookDTO) {
        Shelf shelf = shelfRepository.findByBookIsNull().orElse(new Shelf());
        Book book = buildBookFromClient(bookDTO, shelf);
        bookRepository.save(book);
        shelf.setBook(book);
        shelfRepository.save(shelf);
    }

    private Book buildBookFromClient(BookDTO bookDTO, Shelf shelf) {
        return Book.builder()
                .name(bookDTO.getName())
                .shelf(shelf)
                .authors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()))
                .tags(tagService.getTagsFromStringArray(bookDTO.getTags()))
                .Available(true)
                .build();
    }

    public void updateBook(BookDTO bookDTO) {
        bookRepository.save(getUpdatedBook(bookDTO));
    }

    private Book getUpdatedBook(BookDTO bookDTO) {
        Book book = bookRepository
                .findByName(bookDTO.getName())
                .orElseThrow(() -> new CustomException("book not found"));
        book.setAuthors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()));
        book.setTags(tagService.getTagsFromStringArray(bookDTO.getTags()));
        return book;
    }

    public List<BookDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAllByAvailableIsTrue(pageable)
                .stream()
                .map(this::getBookDTOFromBook)
                .collect(Collectors.toList());
    }
}
