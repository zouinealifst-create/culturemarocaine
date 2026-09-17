package com.maroc.culture.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String originalTitle;

    private String alternativeTitle;

    private String poster;

    private Integer year;

    private Integer duration;

    private String language;

    @Column(columnDefinition = "TEXT")
    private String synopsis;

    // --- Relation Many-to-Many avec Genre ---
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "film_genres",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )

    @Builder.Default
    private Set<Genre> genres = new HashSet<>();
}