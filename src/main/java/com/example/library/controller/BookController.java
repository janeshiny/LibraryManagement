package com.example.library.controller;

import com.example.library.model.Book;
import com.example.library.model.dto.CreateBookDTO;
import com.example.library.model.dto.ResponseBookDTO;
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
    public ResponseEntity<ApiResponse<String>> addBook(@Valid @RequestBody CreateBookDTO book){
        bookService.addBook(book);
        ApiResponse<String> response= ApiResponse.success(null,"Book Added To Shelf");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        ApiResponse<String> response= ApiResponse.success(null,"Book deleted successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateBookDetails(@PathVariable Long id ,@Valid @RequestBody UpdateBookDTO book){
        bookService.updateBookDetail(id,book);
        ApiResponse<String> response= ApiResponse.success(null,"Book Details Updated");
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getAllBooks(){
        List<ResponseBookDTO> bookList= bookService.findAllBooks();
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning all books");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByAuthor(@RequestParam String author){
        List<ResponseBookDTO> bookList= bookService.findBooksByAuthor(author);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books of author "+author);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByTitle(@RequestParam String title){
        List<ResponseBookDTO> bookList= bookService.findBooksByTitle(title);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books containing "+title);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByGenre(@PathVariable GENRE genre){
        List<ResponseBookDTO> bookList= bookService.findBooksByGenre(genre);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books of genre: "+genre);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
