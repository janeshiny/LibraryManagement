package com.example.library.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookDTO {
    @NotBlank
    private String author;
    private GENRE genre;
    private Integer count;
    private Integer year;
}
