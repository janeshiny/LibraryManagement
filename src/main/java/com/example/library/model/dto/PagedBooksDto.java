package com.example.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagedBooksDto {
    List<ResponseBookDTO> bookList;
    int currentPage;
    int totalPages;
    long totalElements;
    int size;
    boolean isLast;
}
