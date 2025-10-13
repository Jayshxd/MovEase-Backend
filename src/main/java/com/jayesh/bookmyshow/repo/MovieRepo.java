package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepo extends JpaRepository<Movie,Long> {
}
