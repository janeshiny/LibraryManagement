package com.example.library.service;

import com.example.library.exception.PageDetailInvalidException;
import com.example.library.exception.PageNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.types.GENRE;
import com.example.library.model.dto.PagedBooksDto;
import com.example.library.model.types.SORT;
import com.example.library.repository.BookRepository;
import com.example.library.utils.BookMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public UserBookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookMapper = bookMapper;
        this.bookRepository = bookRepository;
    }

    @Transactional(readOnly = true)
    public PagedBooksDto findBooks(Integer pageNo, Integer size, SORT sort) {
        Pageable pageable = commonPageValidation(pageNo,size,sort);
        Page<Book> page = bookRepository.findAll(pageable);
        isPageFound(pageNo,page,"");
        return bookMapper.toPagedBookDto(page);
    }

    @Transactional(readOnly = true)
    public PagedBooksDto findBooksByGenre(Integer pageNo, Integer size, SORT sort, GENRE genre) {
        Pageable pageable = commonPageValidation(pageNo,size,sort);
        Page<Book> page = bookRepository.findByGenre(genre,pageable);
        isPageFound(pageNo,page,"for genre "+genre);
        return bookMapper.toPagedBookDto(page);
    }

    @Transactional(readOnly = true)
    public PagedBooksDto findBooksByAuthor(Integer pageNo, Integer size, SORT sort, String author) {
        Pageable pageable = commonPageValidation(pageNo,size,sort);
        Page<Book> page = bookRepository.findByAuthorContainingIgnoreCase(author,pageable);
        isPageFound(pageNo,page,"for author "+author);
        return bookMapper.toPagedBookDto(page);
    }

    @Transactional(readOnly = true)
    public PagedBooksDto findBooksByTitle(Integer pageNo, Integer size, SORT sort, String title) {
        Pageable pageable = commonPageValidation(pageNo,size,sort);
        Page<Book> page = bookRepository.findByTitleContainingIgnoreCase(title,pageable);
        isPageFound(pageNo,page,"for title "+title);
        return bookMapper.toPagedBookDto(page);
    }

    private Pageable commonPageValidation(Integer pageNo, Integer size,SORT sort){
        if(size<1 || size>10)
            throw new PageDetailInvalidException("Size cannot be less than 1 or more than 10");
        if(pageNo<0)
            throw new PageDetailInvalidException("Page no cannot be less than 0");
        Sort sortType = sort ==SORT.ASC
                ? Sort.by("title").ascending()
                : Sort.by("title").descending();
        return PageRequest.of(pageNo,size,sortType);
    }

    private void isPageFound(Integer pageNo, Page<Book> page, String messageSuffix){
        if( pageNo == 0 && page.getTotalElements() == 0){
            throw new PageNotFoundException("No Books Found "+messageSuffix);
        }
        if (pageNo >= page.getTotalPages()) {
            throw new PageNotFoundException("Page No. exceeds the total pages present");
        }
    }
}
