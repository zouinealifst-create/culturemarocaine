package com.maroc.culture.service;

import com.maroc.culture.entity.Film;
import com.maroc.culture.entity.Genre;
import com.maroc.culture.repository.FilmRepository;
import com.maroc.culture.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository; // Injection nécessaire pour vérifier l'existence des genres

    public List<Film> getAllFilms() {
        return filmRepository.findAll();
    }

    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    public void deleteAllFilm() {
        filmRepository.deleteAll();
    }

    @Transactional
    public Film createFilm(Film film) {
        film.setId(null); // Pour forcer la création (INSERT) et éviter d'écraser un ID existant

        // Associer les vraies entités Genre trouvées en base
        if (film.getGenres() != null && !film.getGenres().isEmpty()) {
            Set<Genre> attachedGenres = resolveGenres(film.getGenres());
            film.setGenres(attachedGenres);
        }

        return filmRepository.save(film);
    }

    @Transactional
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

            // Mise à jour de la liste des genres associés
            if (updatedFilm.getGenres() != null) {
                Set<Genre> attachedGenres = resolveGenres(updatedFilm.getGenres());
                film.setGenres(attachedGenres);
            }

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

    /**
     * Méthode utilitaire : prend les objets Genre reçus dans la requête (souvent détachés, contenant juste un ID)
     * et va chercher les entités gérées en base via genreRepository.
     */
    private Set<Genre> resolveGenres(Set<Genre> incomingGenres) {
        Set<Genre> resolved = new HashSet<>();
        for (Genre g : incomingGenres) {
            if (g.getId() != null) {
                genreRepository.findById(g.getId()).ifPresent(resolved::add);
            }
        }
        return resolved;
    }


}