package com.example.library.service;

import com.example.library.exception.BookAlreadyExistsException;
import com.example.library.exception.BookDetailInvalidException;
import com.example.library.exception.BookNotFoundException;
import com.example.library.model.Book;
import com.example.library.model.GENRE;
import com.example.library.model.dto.AdminBookDTO;
import com.example.library.model.dto.CreateBookDTO;
import com.example.library.model.dto.UpdateBookDTO;
import com.example.library.repository.BookRepository;
import com.example.library.utils.BookMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
public class AdminBookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public AdminBookService(BookRepository bookRepository, BookMapper bookMapper){
        this.bookRepository=bookRepository;
        this.bookMapper=bookMapper;
    }

    @Transactional
    public void addBook(CreateBookDTO createBookDTO){
        String title= createBookDTO.getTitle();
        String author= createBookDTO.getAuthor();
        GENRE genre=createBookDTO.getGenre();
        Integer count= createBookDTO.getCount();
        Integer year= createBookDTO.getYear();
        validateBookDetail(title,author,genre,year,count);
        Optional<Book> presentBook = checkBookExistsByAuthorAndTitle(title,author);
        /*  TODO: Change the count implementation later when users are added to account for race conditions */
        presentBook.ifPresent( book ->{throw new BookAlreadyExistsException("Book already exists please update count instead");});
        bookRepository.save(Book.builder()
                        .title(title).author(author).genre(genre).count(count).year(year).build());
    }

    @Transactional
    public void updateBookDetail(Long id, UpdateBookDTO updateBookDTO){
        Book book = checkBookExistsByIdOrElseThrow(id);
        validateUpdateBookDTODetail(updateBookDTO.getAuthor(), updateBookDTO.getYear(), updateBookDTO.getCount(), updateBookDTO.getGenre());

        if(updateBookDTO.getAuthor()!=null){
            Optional<Book> checkBook = checkBookExistsByAuthorAndTitle(book.getTitle(), updateBookDTO.getAuthor());
            checkBook.ifPresent(
                    x->{
                        if(!Objects.equals(x.getId(), book.getId()))
                            throw new BookDetailInvalidException("The updated book details (title+author) are already present in library");}
            );
            book.setAuthor(updateBookDTO.getAuthor());}
        if(updateBookDTO.getGenre()!=null) book.setGenre(updateBookDTO.getGenre());
        if(updateBookDTO.getYear()!=null) book.setYear(updateBookDTO.getYear());
        if(updateBookDTO.getCount()!=null) book.setCount(updateBookDTO.getCount());
    }

    @Transactional
    public void deleteBook(Long id){
        checkBookExistsByIdOrElseThrow(id);
        bookRepository.deleteById(id);
    }

    public AdminBookDTO findBookByTitleAndAuthor(String title, String author){
        return checkBookExistsByAuthorAndTitle(title, author)
                .map(bookMapper::toAdminDto)
                .orElseThrow(()->new BookNotFoundException("No such book found"));

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

    private void validateUpdateBookDTODetail(String author, Integer year, Integer count, GENRE genre){
        if (author == null && genre == null && year == null && count == null) {
            throw new BookDetailInvalidException("No valid fields provided for update");
        }
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

    private Optional<Book> checkBookExistsByAuthorAndTitle(String title, String author){
        return bookRepository.findByTitleIgnoreCaseAndAuthorIgnoreCase(title,author);
    }

    private Book checkBookExistsByIdOrElseThrow(Long id){
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("No Book Exists In This ID"));
    }
}
