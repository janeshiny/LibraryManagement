package com.example.library.model.dto;

import com.example.library.model.types.GENRE;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBookDTO {
    private String title;
    private String author;
    private GENRE genre;
    private Integer count;
    private Integer year;
}
