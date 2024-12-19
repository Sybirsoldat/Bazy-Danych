package com.example.library.services;

import com.example.library.models.Genre;
import com.example.library.repositories.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
    }

    public Genre getGenreByName(String name) {
        return genreRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
    }

    public Genre createGenre(Genre genre) {
        if (genreRepository.findByNameIgnoreCase(genre.getName()).isPresent()) {
            throw new RuntimeException("Genre already exists");
        }
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long id, Genre genreDetails) {
        Genre genre = getGenreById(id);
        genre.setName(genreDetails.getName());
        return genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        Genre genre = getGenreById(id);
        genreRepository.delete(genre);
    }
}
