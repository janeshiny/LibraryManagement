package com.example.library.repository;

import com.example.library.model.Book;
import com.example.library.model.types.GENRE;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByGenre(GENRE genre);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByTitleContainingIgnoreCase(String title);

    Optional<Book> findByTitleIgnoreCaseAndAuthorIgnoreCase(String title, String author);

    Page<Book> findByGenre(GENRE genre, Pageable page);

    Page<Book> findByAuthorContainingIgnoreCase(String author, Pageable page);

    Page<Book> findByTitleContainingIgnoreCase(String title, Pageable page);
}
