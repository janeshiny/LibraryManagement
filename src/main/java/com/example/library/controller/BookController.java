package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.dto.CreateBookDTO;
import com.example.library.model.dto.UpdateBookDTO;
import com.example.library.model.GENRE;
import com.example.library.model.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks(){
        List<Book> bookList= bookService.findAllBooks();
        ApiResponse<List<Book>> response= ApiResponse.success(bookList,"Returning all books");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByAuthor(@RequestParam String author){
        List<Book> bookList= bookService.findBooksByAuthor(author);
        ApiResponse<List<Book>> response= ApiResponse.success(bookList,"Returning books of author"+author);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByTitle(@RequestParam String title){
        List<Book> bookList= bookService.findBooksByTitle(title);
        ApiResponse<List<Book>> response= ApiResponse.success(bookList,"Returning books containing "+title);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/{genre}")
    public ResponseEntity<ApiResponse<List<Book>>> getBooksByGenre(@PathVariable GENRE genre){
        List<Book> bookList= bookService.findBooksByGenre(genre);
        ApiResponse<List<Book>> response= ApiResponse.success(bookList,"Returning books of genre: "+genre);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
