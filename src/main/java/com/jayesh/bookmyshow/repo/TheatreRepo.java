package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepo extends JpaRepository<Theatre,Long> {
}
