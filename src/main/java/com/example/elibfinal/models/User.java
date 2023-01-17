package com.example.elibfinal.models;

import com.example.elibfinal.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private Status status;


    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Role role;

    private String email;
    private String phone;
    private boolean active;

    public User(String username, String password, Status status, Role role, String email, String phone, boolean active) {
        this.username = username;
        this.password = password;
        this.status = status;
        this.role = role;
        this.email = email;
        this.phone = phone;
        this.active = active;
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.phone = userDTO.getPhone();
        this.email = userDTO.getEmail();
        this.role = Role.USER_ROLE;
        this.status = Status.ACTIVE;
        active = true;
    }
}
