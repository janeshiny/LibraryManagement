package com.example.library.controller;

import com.example.library.model.dto.AdminBookDTO;
import com.example.library.model.dto.CreateBookDTO;
import com.example.library.model.dto.UpdateBookDTO;
import com.example.library.model.response.ApiResponse;
import com.example.library.service.AdminBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/books")
public class AdminBookController {

    private final AdminBookService adminBookService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> addBook(@Valid @RequestBody CreateBookDTO book){
        adminBookService.addBook(book);
        ApiResponse<String> response= ApiResponse.success(null,"Book Added To Shelf");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBook(@PathVariable Long id){
        adminBookService.deleteBook(id);
        ApiResponse<String> response= ApiResponse.success(null,"Book deleted successfully");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> updateBookDetails(@PathVariable Long id ,@Valid @RequestBody UpdateBookDTO book){
        adminBookService.updateBookDetail(id,book);
        ApiResponse<String> response= ApiResponse.success(null,"Book Details Updated");
        return new ResponseEntity<>(response,HttpStatus.ACCEPTED);
    }

    @GetMapping("/find")
    public ResponseEntity<ApiResponse<AdminBookDTO>> findBookByTitleAndAuthor(@RequestParam String title ,@RequestParam String author){
        AdminBookDTO bookDTO= adminBookService.findBookByTitleAndAuthor(title, author);
        ApiResponse<AdminBookDTO> response=ApiResponse.success(bookDTO, "Returning book");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
