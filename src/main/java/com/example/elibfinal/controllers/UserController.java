package com.example.elibfinal.controllers;

import com.example.elibfinal.DTO.UserDTO;
import com.example.elibfinal.models.User;
import com.example.elibfinal.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reg")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('developers:read')")
    public void saveNewUser(@RequestBody UserDTO userDTO) {
        userService.saveNewUser(userDTO);
    }
    @PostMapping("/admin/user/ban/{id}")
    @PreAuthorize("hasAuthority('developers:write')")
    public HttpStatus userBan(@PathVariable("id") Long id) {
        userService.banUser(id);
        return HttpStatus.LOCKED;
    }

    @GetMapping("/check/user")
    @PreAuthorize("hasAuthority('developers:read')")
    public User saveNewUser(@RequestParam("username") String username) {
        return userService.loadUserByUsername(username);
    }
}
