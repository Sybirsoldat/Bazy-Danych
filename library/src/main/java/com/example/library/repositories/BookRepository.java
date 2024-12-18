package com.example.library.repositories;

import com.example.library.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByGenre_Id(Long genreId);
    List<Book> findByPublishYear(int year);
    List<Book> findByPriceBetween(int minPrice, int maxPrice);
}