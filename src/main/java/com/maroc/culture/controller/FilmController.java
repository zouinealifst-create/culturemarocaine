package com.maroc.culture.controller;

import com.maroc.culture.entity.Film;
import com.maroc.culture.service.FilmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    // 1. Récupérer tous les films (200 OK)
    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    // 2. Récupérer un film par son ID (200 OK ou 404 NOT FOUND)
    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long id) {
        return filmService.getFilmById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Créer un nouveau film (201 CREATED)
    @PostMapping
    public ResponseEntity<Film> createFilm(@RequestBody Film film) {
        Film createdFilm = filmService.createFilm(film);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFilm);
    }

    // 4. Mettre à jour un film existant (200 OK ou 404 NOT FOUND)
    @PutMapping("/{id}")
    public ResponseEntity<Film> updateFilm(
            @PathVariable Long id,
            @RequestBody Film film) {
        return filmService.updateFilm(id, film)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5. Supprimer un film (204 NO CONTENT ou 404 NOT FOUND)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilm(@PathVariable Long id) {
        if (filmService.deleteFilm(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAllFilms() {
        filmService.deleteAllFilm();
        return ResponseEntity.noContent().build(); // كيرجع 204 No Content
    }
}