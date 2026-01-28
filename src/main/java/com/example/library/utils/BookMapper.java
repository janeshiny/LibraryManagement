package com.example.library.utils;

import com.example.library.model.Book;
import com.example.library.model.dto.AdminBookDTO;
import com.example.library.model.dto.PagedBooksDto;
import com.example.library.model.dto.ResponseBookDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    ResponseBookDTO toDto(Book book);
    List<ResponseBookDTO> toDtoList(List<Book> bookList);

    AdminBookDTO toAdminDto(Book book);

    default PagedBooksDto toPagedBookDto(Page<Book> page){
        List<ResponseBookDTO> responseBookDTOS= toDtoList(page.getContent());
        return PagedBooksDto.builder()
                .bookList(responseBookDTOS)
                .currentPage(page.getNumber())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .size(page.getSize())
                .isLast(page.isLast())
                .build();
    }
}
