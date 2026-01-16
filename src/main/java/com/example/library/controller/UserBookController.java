package com.example.library.controller;

import com.example.library.model.GENRE;
import com.example.library.model.dto.ResponseBookDTO;
import com.example.library.model.response.ApiResponse;
import com.example.library.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/books")
public class UserBookController {

    private final UserBookService userBookService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getAllBooks(){
        List<ResponseBookDTO> bookList= userBookService.findAllBooks();
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning all books");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByAuthor(@RequestParam String author){
        List<ResponseBookDTO> bookList= userBookService.findBooksByAuthor(author);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books of author "+author);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByTitle(@RequestParam String title){
        List<ResponseBookDTO> bookList= userBookService.findBooksByTitle(title);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books containing "+title);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<List<ResponseBookDTO>>> getBooksByGenre(@PathVariable GENRE genre){
        List<ResponseBookDTO> bookList= userBookService.findBooksByGenre(genre);
        ApiResponse<List<ResponseBookDTO>> response= ApiResponse.success(bookList,"Returning books of genre: "+genre);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
