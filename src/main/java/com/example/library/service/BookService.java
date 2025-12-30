package com.example.library.service;

import com.example.library.exception.BookDetailInvalidException;
import com.example.library.model.Book;
import com.example.library.model.GENRE;
import com.example.library.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){
        this.bookRepository=bookRepository;
    }

    @Transactional
    public void addBook(String title, String author, GENRE genre, Integer year, Integer count){
        validateBookDetail(title,author,genre,year,count);
        Optional<Book> presentBook = checkBookExistsByAuthorAndTitle(title,author);
        /*  TODO: Change the count implementation later when users are added to account for race conditions */
        presentBook.ifPresentOrElse(
                book -> {
                    book.setCount(book.getCount()+1);
                    bookRepository.save(book);
                },
                ()-> {
                    bookRepository.save(Book.builder()
                            .title(title).author(author).genre(genre).count(count).year(year).build());
                }
        );

    }

    private void validateBookDetail(String title, String author, GENRE genre, Integer year, Integer count){
        if(title==null||title.trim().isEmpty()||title.isBlank()){
            throw new BookDetailInvalidException("Title Cannot Be Empty");
        }
        if(title.length()<3||title.length()>255){
            throw new BookDetailInvalidException("Title doesn't meet length requirements");
        }
        if(author==null||author.trim().isEmpty()||author.isBlank()){
            throw new BookDetailInvalidException("Author Cannot Be Empty");
        }
        if(author.length()<3||author.length()>255){
            throw new BookDetailInvalidException("Author doesn't meet length requirements");
        }
        if(genre==null){
            throw new BookDetailInvalidException("Genre cannot be null");
        }
        if(year!=null && (year < 0 || year > LocalDate.now().getYear())){
            throw new BookDetailInvalidException("The given year detail is invalid");
        }
        if(count<1){
            throw new BookDetailInvalidException("Count cannot be less than 1");
        }
    }

    private Optional<Book> checkBookExistsByAuthorAndTitle(String title, String author){
        return bookRepository.findByTitleIgnoreCaseAndAuthorIgnoreCase(title,author);
    }
}
