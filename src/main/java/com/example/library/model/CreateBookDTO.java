package com.example.library.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    private GENRE genre;
    @NotNull
    @Min(value = 1)
    private Integer count;
    @Min(value = 0)
    private Integer year;
}
