package com.example.library.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @NotBlank
    @Size(min = 3, max=255)
    @Column(unique = true)
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
    @Column(name = "publish_year")
    private Integer year;
}
