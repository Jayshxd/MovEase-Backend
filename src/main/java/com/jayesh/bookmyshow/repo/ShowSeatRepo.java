package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ShowSeatRepo extends JpaRepository<ShowSeat,Long> {
    boolean existsShowSeatById(Long id);


}
