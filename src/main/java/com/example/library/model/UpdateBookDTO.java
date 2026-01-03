package com.example.library.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBookDTO {
    private String author;
    private GENRE genre;
    private Integer count;
    private Integer year;
}
