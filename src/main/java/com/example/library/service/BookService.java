package com.example.library.service;

import com.example.library.exception.BookDetailInvalidException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.BookDTO;
import com.example.library.model.GENRE;
import com.example.library.repository.BookRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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
                },
                ()-> {
                    bookRepository.save(Book.builder()
                            .title(title).author(author).genre(genre).count(count).year(year).build());
                }
        );

    }

    @Transactional
    public void updateBookDetail(Long id, BookDTO bookDTO){
        Book book = checkBookExistsByIdOrElseThrow(id);
        validateBookDTODetail(bookDTO.getAuthor(), bookDTO.getYear(),bookDTO.getCount());

        if(bookDTO.getAuthor()!=null){
            Optional<Book> checkBook = checkBookExistsByAuthorAndTitle(book.getTitle(),bookDTO.getAuthor());
            checkBook.ifPresent(
                    x->{
                        if(!Objects.equals(x.getId(), book.getId()))
                            throw new BookDetailInvalidException("The updated book details (title+author) are already present in library");}
            );
            book.setAuthor(bookDTO.getAuthor());}
        if(bookDTO.getGenre()!=null) book.setGenre(bookDTO.getGenre());
        if(bookDTO.getYear()!=null) book.setYear(bookDTO.getYear());
        if(bookDTO.getCount()!=null) book.setCount(bookDTO.getCount());
    }

    @Transactional(readOnly = true)
    public List<Book> findAllBooks(){
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByGenre(GENRE genre){
        return bookRepository.findByGenre(genre);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByAuthor(String author){
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    @Transactional(readOnly = true)
    public List<Book> findBooksByTitle(String title){
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    @Transactional
    public void deleteBook(Long id){
        checkBookExistsByIdOrElseThrow(id);
        bookRepository.deleteById(id);
    }

    private void validateBookDTODetail(String author, Integer year, Integer count){
        if(author!=null &&( author.length()<3||author.length()>255)){
            throw new BookDetailInvalidException("Author doesn't meet length requirements");
        }
        if(year!=null && (year < 0 || year > LocalDate.now().getYear())){
            throw new BookDetailInvalidException("The given year detail is invalid");
        }
        if(count!=null && count<1){
            throw new BookDetailInvalidException("Count cannot be less than 1");
        }
    }

    private void validateBookDetail(String title, String author, GENRE genre, Integer year, Integer count){
        if(title.isBlank() || title.length()<3||title.length()>255){
            throw new BookDetailInvalidException("Title doesn't meet length requirements");
        }

        if(author.isBlank()|| author.length()<3||author.length()>255){
            throw new BookDetailInvalidException("Author doesn't meet length requirements");
        }
        if(genre==null){
            throw new BookDetailInvalidException("Genre cannot be null");
        }
        if(year!=null && (year < 0 || year > LocalDate.now().getYear())){
            throw new BookDetailInvalidException("The given year detail is invalid");
        }
        if(count==null || count<1){
            throw new BookDetailInvalidException("Count cannot be less than 1");
        }
    }

    private Optional<Book> checkBookExistsByAuthorAndTitle(String title, String author){
        return bookRepository.findByTitleIgnoreCaseAndAuthorIgnoreCase(title,author);
    }

    private Book checkBookExistsByIdOrElseThrow(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No Book Exists In This ID"));
    }
}
