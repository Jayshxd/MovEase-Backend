package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepo extends JpaRepository<Seat,Long> {
    // Yeh query tab chalegi jab screenId bhi diya ho
    @Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId AND " +
            "(:seatNumber IS NULL OR LOWER(s.seatNumber) LIKE LOWER(CONCAT('%', :seatNumber, '%'))) AND " +
            "(:seatType IS NULL OR LOWER(s.seatType) LIKE LOWER(CONCAT('%', :seatType, '%')))")
    List<Seat> findByScreenIdAndCriteria(@Param("screenId") Long screenId,
                                         @Param("seatNumber") String seatNumber,
                                         @Param("seatType") String seatType);

    // Yeh query tab chalegi jab screenId nahi diya ho (sab seats me search karna hai)
    @Query("SELECT s FROM Seat s WHERE " +
            "(:seatNumber IS NULL OR LOWER(s.seatNumber) LIKE LOWER(CONCAT('%', :seatNumber, '%'))) AND " +
            "(:seatType IS NULL OR LOWER(s.seatType) LIKE LOWER(CONCAT('%', :seatType, '%')))")
    List<Seat> findByCriteria(@Param("seatNumber") String seatNumber,
                              @Param("seatType") String seatType);
}
