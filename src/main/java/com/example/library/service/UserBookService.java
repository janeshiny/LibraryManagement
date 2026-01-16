package com.example.library.service;

import com.example.library.model.GENRE;
import com.example.library.model.dto.ResponseBookDTO;
import com.example.library.repository.BookRepository;
import com.example.library.utils.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public UserBookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookMapper=bookMapper;
        this.bookRepository=bookRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDTO> findAllBooks(){
        return bookMapper.toDtoList(bookRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDTO> findBooksByGenre(GENRE genre){
        return bookMapper.toDtoList(bookRepository.findByGenre(genre));
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDTO> findBooksByAuthor(String author){
        return bookMapper.toDtoList(bookRepository.findByAuthorContainingIgnoreCase(author));
    }

    @Transactional(readOnly = true)
    public List<ResponseBookDTO> findBooksByTitle(String title){
        return bookMapper.toDtoList(bookRepository.findByTitleContainingIgnoreCase(title));
    }
}
