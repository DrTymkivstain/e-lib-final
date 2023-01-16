package com.example.elibfinal.services;

import com.example.elibfinal.DTO.BookDTO;
import com.example.elibfinal.DTO.OrderDTO;
import com.example.elibfinal.exception.CustomException;
import com.example.elibfinal.models.Book;
import com.example.elibfinal.models.Order;
import com.example.elibfinal.models.Shelf;
import com.example.elibfinal.repository.BookRepository;
import com.example.elibfinal.repository.OrderRepository;
import com.example.elibfinal.repository.ShelfRepository;
import com.example.elibfinal.repository.UserRepository;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private static final Integer PERIOD_OF_USE = 1;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ShelfRepository shelfRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, UserRepository userRepository, ShelfRepository shelfRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.shelfRepository = shelfRepository;
    }

    public void createAndSaveNewOrder(BookDTO bookDTO, String username) {
        orderRepository.save(buildNewOrder(bookDTO, username));
    }

    private Order buildNewOrder(BookDTO bookDTO, String username) {
        return Order.builder()
                .user(userRepository.findByUsername(username)
                        .orElseThrow(() -> new CustomException("user not found")))
                .book(bookRepository.findByName(bookDTO.getName())
                        .orElseThrow(() -> new CustomException("book not found")))
                .active(false)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
    }

    public void permitOrder(OrderDTO orderDTO) {
        orderRepository.save(activateAndChangeOrder(orderDTO));
    }

    private Order activateAndChangeOrder(OrderDTO orderDTO) {
        Order order = orderRepository
                .findById(orderDTO.getId())
                .orElseThrow(() -> new CustomException("order not found"));
        if (!order.getBook().isAvailable()) throw new CustomException("book not available");
        order.setActive(true);
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(PERIOD_OF_USE));
        order.getBook().setAvailable(false);
        return order;
    }

    public List<OrderDTO> getActiveOrders() {
        return orderRepository
                .findAllByActiveIsTrue()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getPassiveOrders() {
        return orderRepository.findAllByActiveIsFalse()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getActiveOrdersByUserName(String name) {
        return orderRepository.findAllByActiveIsTrueAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
                .bookName(order.getBook().getName())
                .id(order.getOrderId())
                .userName(order.getUser().getUsername())
                .endDate(order.getEndDate()
                        .format(getFormatterByLocale()))
                .startDate(order.getStartDate()
                        .format(getFormatterByLocale()))
                .build();
    }

    private DateTimeFormatter getFormatterByLocale() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(LocaleContextHolder.getLocale());
    }

    public List<OrderDTO> getPassiveOrdersByUserName(String name) {
        return orderRepository.findAllByActiveIsFalseAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public void returnBook(OrderDTO orderDTO) {
        saveBookAndDeleteOrder(getOrderAndPrepareBookForReturning(orderDTO));
    }

    @Transactional
    void saveBookAndDeleteOrder(Order order) {
        bookRepository.save(order.getBook());
        orderRepository.delete(order);
    }

    private Order getOrderAndPrepareBookForReturning(OrderDTO orderDTO) {
        Order order = orderRepository
                .findById(orderDTO.getId())
                .orElseThrow(() -> new CustomException("order not found"));
        prepareBookForReturning(order.getBook());
        return order;
    }

    private void prepareBookForReturning(Book book) {
        book.setAvailable(true);
        book.setShelf(shelfRepository.findByBookIsNull().orElse(new Shelf()));
    }
}
