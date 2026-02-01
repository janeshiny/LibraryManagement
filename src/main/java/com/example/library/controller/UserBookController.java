package com.example.library.controller;

import com.example.library.model.dto.PagedBooksDto;
import com.example.library.model.types.GENRE;
import com.example.library.model.response.ApiResponse;
import com.example.library.model.types.SORT;
import com.example.library.service.UserBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/library/books")
public class UserBookController {

    private final UserBookService userBookService;

    @GetMapping
    public ResponseEntity<ApiResponse<PagedBooksDto>> getBooks(@RequestParam Integer pageNo, @RequestParam Integer size,
                                                               @RequestParam SORT sort){
        PagedBooksDto pagedBooksDto= userBookService.findBooks(pageNo, size, sort);
        ApiResponse<PagedBooksDto> response=ApiResponse.success(pagedBooksDto,"Returning all books");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/author")
    public ResponseEntity<ApiResponse<PagedBooksDto>> getBooksByAuthor(@RequestParam String author,@RequestParam Integer pageNo,
                                                                       @RequestParam Integer size, @RequestParam SORT sort){
        PagedBooksDto pagedBooksDto= userBookService.findBooksByAuthor(pageNo, size, sort,author);
        ApiResponse<PagedBooksDto> response= ApiResponse.success(pagedBooksDto,"Returning books of author "+author);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<ApiResponse<PagedBooksDto>> getBooksByTitle(@RequestParam String title,@RequestParam Integer pageNo,
                                                                      @RequestParam Integer size, @RequestParam SORT sort){
        PagedBooksDto pagedBooksDto= userBookService.findBooksByTitle(pageNo, size, sort,title);
        ApiResponse<PagedBooksDto> response= ApiResponse.success(pagedBooksDto,"Returning books containing "+title);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @GetMapping("/genre/{genre}")
    public ResponseEntity<ApiResponse<PagedBooksDto>> getBooksByGenre(@PathVariable GENRE genre,@RequestParam Integer pageNo,
                                                                      @RequestParam Integer size, @RequestParam SORT sort){
        PagedBooksDto pagedBooksDto= userBookService.findBooksByGenre(pageNo, size, sort,genre);
        ApiResponse<PagedBooksDto> response= ApiResponse.success(pagedBooksDto,"Returning books of genre: "+genre);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

}
