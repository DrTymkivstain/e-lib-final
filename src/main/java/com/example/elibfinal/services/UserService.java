package com.example.elibfinal.services;

import com.example.elibfinal.DTO.UserDTO;
import com.example.elibfinal.exception.CustomException;
import com.example.elibfinal.models.Role;
import com.example.elibfinal.models.Status;
import com.example.elibfinal.models.User;
import com.example.elibfinal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UserService {
    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void saveNewUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("user.already.exist");
        }
    }
    private void saveNewUserAsAdmin(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("user.already.exist");
        }
    }
    @PostConstruct
    private void postConstruct() {
        User admin = new User("admin3","admin", Status.ACTIVE, Role.ADMIN_ROLE, "@","1",true);
        this.saveNewUserAsAdmin(admin);
    }

    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException("user not found"));
    }

    public void banUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException("user not found"));
        if (user.getStatus().equals(Status.ACTIVE)) {
            user.setStatus(Status.Banned);
        } else {
            user.setStatus(Status.ACTIVE);
        }
        userRepository.save(user);
    }

}
