package com.maroc.culture.repository;

import com.maroc.culture.entity.FilmRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRoleRepository extends JpaRepository<FilmRole, Long> {
    List<FilmRole> findByFilmId(Long filmId);
}