package com.example.library.model.dto;

import com.example.library.model.types.GENRE;
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
