package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Size(min = 3, max=255)
    private String title;

    @NotBlank
    @Size(min = 3, max=255)
    private String author;

    @NotNull
    @Enumerated(EnumType.STRING)
    private GENRE genre;

    @Min(value = 1)
    @NotNull
    private Integer count;

    @Min(value = 0)
    private Integer year;
}
