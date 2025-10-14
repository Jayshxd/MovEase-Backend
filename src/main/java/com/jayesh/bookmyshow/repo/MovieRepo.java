package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Long> {
    List<Movie> findAllByTitle(String title);

    List<Movie> findAllByDescription(String description);

    List<Movie> findAllByGenre(String genre);

    List<Movie> findAllByLanguage(String language);

    List<Movie> findAllByReleaseDate(LocalDate releaseDate);

    List<Movie> findAllByDuration(String duration);
}
