package com.example.elibfinal.controllers;

import com.example.elibfinal.DTO.OrderDTO;
import com.example.elibfinal.services.OrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/permit")
    @PreAuthorize("hasAuthority('developers:read')")
    public OrderDTO permitOrder(@RequestBody OrderDTO orderDTO) {
        orderService.permitOrder(orderDTO);
        return orderDTO;
    }

    @GetMapping("/active")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<OrderDTO> getActiveOrders() {
        return orderService.getActiveOrders();
    }

    @GetMapping("/passive")
    @PreAuthorize("hasAuthority('developers:write')")
    public List<OrderDTO> getPassiveOrders() {
        return orderService.getPassiveOrders();
    }

    @GetMapping("/user/active")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<OrderDTO> getActiveOrdersByUser(Authentication authentication) {
        return orderService.getActiveOrdersByUserName(authentication.getName());
    }

    @GetMapping("/user/passive")
    @PreAuthorize("hasAuthority('developers:read')")
    public List<OrderDTO> getPassiveOrdersByUser(Authentication authentication) {
        return orderService.getPassiveOrdersByUserName(authentication.getName());
    }

    @PutMapping("/user/return")
    @PreAuthorize("hasAuthority('developers:read')")
    public OrderDTO returnBook(@RequestBody OrderDTO orderDTO) {
        orderService.returnBook(orderDTO);
        return orderDTO;
    }
}
