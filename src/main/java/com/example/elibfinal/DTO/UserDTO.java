package com.example.elibfinal.DTO;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String password;
}