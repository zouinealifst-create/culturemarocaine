package com.maroc.culture.service;

import com.maroc.culture.entity.Film;
import com.maroc.culture.repository.FilmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// La suite de ton code reste identique à partir d'ici :
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    public Film createFilm(Film film) {
        return filmRepository.save(film);
    }

    public Optional<Film> updateFilm(Long id, Film updatedFilm) {
        return filmRepository.findById(id).map(film -> {
            film.setTitle(updatedFilm.getTitle());
            film.setOriginalTitle(updatedFilm.getOriginalTitle());
            film.setAlternativeTitle(updatedFilm.getAlternativeTitle());
            film.setPoster(updatedFilm.getPoster());
            film.setYear(updatedFilm.getYear());
            film.setDuration(updatedFilm.getDuration());
            film.setLanguage(updatedFilm.getLanguage());
            film.setSynopsis(updatedFilm.getSynopsis());
            return filmRepository.save(film);
        });
    }

    public boolean deleteFilm(Long id) {
        if (filmRepository.existsById(id)) {
            filmRepository.deleteById(id);
            return true;
        }
        return false;
    }
}