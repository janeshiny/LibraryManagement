package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.CreateBookDTO;
import com.example.library.model.UpdateBookDTO;
import com.example.library.model.GENRE;
import com.example.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<String> addBook(@Valid @RequestBody CreateBookDTO book){
        bookService.addBook(book);
        return new ResponseEntity<>("Book Added To Shelf", HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateBookDetails(@PathVariable Long id ,@Valid @RequestBody UpdateBookDTO book){
        bookService.updateBookDetail(id,book);
        return new ResponseEntity<>("Book Details Updated",HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(){
        List<Book> bookList= bookService.findAllBooks();
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<List<Book>> getBooksByAuthor(@RequestParam String author){
        List<Book> bookList= bookService.findBooksByAuthor(author);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<Book>> getBooksByTitle(@RequestParam String title){
        List<Book> bookList= bookService.findBooksByTitle(title);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable GENRE genre){
        List<Book> bookList= bookService.findBooksByGenre(genre);
        return new ResponseEntity<>(bookList,HttpStatus.OK);
    }

}
