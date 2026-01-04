package com.example.library.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBookDTO {
    @NotBlank(message = "Title should never be blank")
    private String title;
    @NotBlank(message = "Author should never be blank")
    private String author;
    @NotNull(message = "Genre should never be blank")
    private GENRE genre;
    @NotNull(message = "Count shouldn't be blank and less than 1")
    @Min(value = 1)
    private Integer count;
    @Min(value = 0, message = "Year should never be less than 0")
    private Integer year;
}
