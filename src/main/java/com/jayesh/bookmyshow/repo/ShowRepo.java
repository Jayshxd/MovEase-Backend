package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShowRepo extends JpaRepository<Show,Long> {

    @Query("SELECT s FROM Show s WHERE " +
            "(:showDate IS NULL OR s.showDate = :showDate) AND " +
            "(:showTime IS NULL OR s.showTime = :showTime) AND " +
            "(:ticketPrice IS NULL OR s.ticketPrice = :ticketPrice) AND " +
            "(:movieId IS NULL OR s.movie.id = :movieId) AND " +
            "(:screenId IS NULL OR s.screen.id = :screenId)")
    List<Show> findShowsByCriteria(
            @Param("showDate") LocalDate showDate,
            @Param("showTime") LocalTime showTime,
            @Param("ticketPrice") Double ticketPrice, // Note: Changed to Double
            @Param("movieId") Long movieId,
            @Param("screenId") Long screenId
    );

    List<Show> findAllShowsByMovieId(Long movieId);
}
