package com.maroc.culture.service;

import com.maroc.culture.dto.RoleRequest;
import com.maroc.culture.entity.Film;
import com.maroc.culture.entity.FilmRole;
import com.maroc.culture.entity.Person;
import com.maroc.culture.repository.FilmRepository;
import com.maroc.culture.repository.FilmRoleRepository;
import com.maroc.culture.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmRoleService {

    private final FilmRoleRepository filmRoleRepository;
    private final FilmRepository filmRepository;
    private final PersonRepository personRepository;

    public FilmRole assignRoleToFilm(Long filmId, RoleRequest request) {
        Film film = filmRepository.findById(filmId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Film non trouvé avec l'id: " + filmId));

        Person person = personRepository.findById(request.getPersonId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Personne non trouvée avec l'id: " + request.getPersonId()));

        FilmRole filmRole = FilmRole.builder()
                .film(film)
                .person(person)
                .role(request.getRole())
                .build();

        return filmRoleRepository.save(filmRole);
    }

    public List<FilmRole> getRolesByFilmId(Long filmId) {
        if (!filmRepository.existsById(filmId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Film non trouvé avec l'id: " + filmId);
        }
        return filmRoleRepository.findByFilmId(filmId);
    }
}