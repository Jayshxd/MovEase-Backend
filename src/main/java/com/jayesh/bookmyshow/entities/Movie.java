package com.jayesh.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    private String genre;

    private String language;

    private String duration;

    private LocalDate releaseDate;

    public Movie(String title, String description, String genre, String language, String duration, LocalDate releaseDate) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.language = language;
        this.duration = duration;
        this.releaseDate = releaseDate;
    }

    @OneToMany(mappedBy = "movie",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Show> shows;

    //helper
    public void addShow(Show show){
        this.shows.add(show);
        show.setMovie(this);
    }
    public void removeShow(Show show){
        this.shows.remove(show);
        show.setMovie(null);
    }
}
