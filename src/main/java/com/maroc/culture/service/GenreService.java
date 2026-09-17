package com.maroc.culture.service;

import com.maroc.culture.entity.Genre;
import com.maroc.culture.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

       /*
    pas besoin de cree ce constructeur par ce que en utilise @RequiredArgsConstructor

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }*/

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long id) {
        return genreRepository.findById(id);
    }

    public Genre createGenre(Genre genre) {
        if (genreRepository.existsByName(genre.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ce genre existe déjà !");
        }
        return genreRepository.save(genre);
    }


    public Optional<Genre> updateGenre(Long id, Genre updatedGenre) {
        return genreRepository.findById(id).map(existingGenre -> {
            existingGenre.setName(updatedGenre.getName());
            existingGenre.setDescription(updatedGenre.getDescription());
            return genreRepository.save(existingGenre);
        });
    }

    public boolean deleteGenre(Long id) {
        if (genreRepository.existsById(id)) {
            genreRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void deleteAllGenres() {
        genreRepository.deleteAll();
    }
}