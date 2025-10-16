package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat,Long> {
}
