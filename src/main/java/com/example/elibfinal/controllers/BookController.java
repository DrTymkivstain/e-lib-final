package com.example.elibfinal.controllers;

import com.example.elibfinal.DTO.BookDTO;
import com.example.elibfinal.models.Book;
import com.example.elibfinal.services.BookService;
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
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookService.findAllBooks();
        return bookService.findAllBooks()
                .stream()
                .map(bookService::getBookDTOFromBook)
                .collect(Collectors.toList());
    }
    @PostMapping("/add")
    public BookDTO addNewBook(@RequestBody BookDTO bookDTO) {
        bookService.saveNewBookFromClient(bookDTO);
        return bookDTO;
    }

    @PutMapping("/update")
    public BookDTO updateBook( BookDTO bookDTO){
        bookService.updateBook(bookDTO);
        return bookDTO;
    }

}
