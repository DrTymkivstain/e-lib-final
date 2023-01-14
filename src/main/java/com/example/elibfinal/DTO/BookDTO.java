package com.example.elibfinal.DTO;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BookDTO {
    private String name;
    private String[] tags;
    private String[] authors;
}
