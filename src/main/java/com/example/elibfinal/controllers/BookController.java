package com.example.elibfinal.controllers;

import com.example.elibfinal.DTO.BookDTO;
import com.example.elibfinal.models.Book;
import com.example.elibfinal.services.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/lib")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return bookService.findAllBooks()
                .stream()
                .map(bookService::getBookDTOFromBook)
                .collect(Collectors.toList());
    }
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('developers:write')")
    public BookDTO addNewBook(@RequestBody BookDTO bookDTO) {
        bookService.saveNewBookFromClient(bookDTO);
        return bookDTO;
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('developers:write')")
    public BookDTO updateBook( BookDTO bookDTO){
        bookService.updateBook(bookDTO);
        return bookDTO;
    }

}
