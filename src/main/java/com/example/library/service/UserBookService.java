package com.example.library.service;

import com.example.library.exception.PageDetailInvalidException;
import com.example.library.exception.PageNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.types.GENRE;
import com.example.library.model.dto.PagedBooksDto;
import com.example.library.model.dto.ResponseBookDTO;
import com.example.library.model.types.SORT;
import com.example.library.repository.BookRepository;
import com.example.library.utils.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Transactional(readOnly = true)
    public PagedBooksDto findAllBooks1(Integer pageNo, Integer size, SORT sort){
        if(size<1 || size>10)
            throw new PageDetailInvalidException("Size cannot be less than 1 or more than 10");
        if(pageNo<0)
            throw new PageDetailInvalidException("Page no cannot be less than 0");

        Sort sortType = sort == SORT.ASC
                ? Sort.by("title").ascending()
                : Sort.by("title").descending();
        Pageable pageable = PageRequest.of(pageNo,size,sortType);
        Page<Book> page = bookRepository.findAll(pageable);
        if(pageNo>=page.getTotalPages()){
            throw new PageNotFoundException("Page No. exceeds the total pages present");
        }
        return bookMapper.toPagedBookDto(page);
    }
}
