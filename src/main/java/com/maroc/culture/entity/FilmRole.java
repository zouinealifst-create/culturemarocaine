package com.maroc.culture.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(nullable = false)
    private String role; // "Réalisateur", "Acteur", "Scénariste"
}