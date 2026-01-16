package com.example.library.utils;

import com.example.library.model.Book;
import com.example.library.model.dto.ResponseBookDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    ResponseBookDTO toDto(Book book);
    List<ResponseBookDTO> toDtoList(List<Book> bookList);
}
