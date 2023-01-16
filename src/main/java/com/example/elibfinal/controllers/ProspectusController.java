package com.example.elibfinal.controllers;

import com.example.elibfinal.DTO.BookDTO;
import com.example.elibfinal.models.Author;
import com.example.elibfinal.models.Tag;
import com.example.elibfinal.services.AuthorService;
import com.example.elibfinal.services.BookService;
import com.example.elibfinal.services.OrderService;
import com.example.elibfinal.services.TagService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ProspectusController {
    private final BookService bookService;
    private final TagService tagService;
    private final AuthorService authorService;
    private final OrderService orderService;

    public ProspectusController(BookService bookService, TagService tagService, AuthorService authorService, OrderService orderService) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.authorService = authorService;
        this.orderService = orderService;
    }

    @GetMapping(value = "/books/{page}/{number}")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<BookDTO> getAvailableBooks(@PathVariable("page") String page, @PathVariable("number") String number) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(number));
        return bookService.getAvailableBooks(pageable);
    }

    @GetMapping(value = "/tags")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(value = "/authors")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/order")
    @PreAuthorize("hasAuthority('developers:read')")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus orderBook(@RequestBody BookDTO bookDTO, Authentication authentication) {
        orderService.createAndSaveNewOrder(bookDTO, authentication.getName());
        return HttpStatus.CREATED;
    }
}
