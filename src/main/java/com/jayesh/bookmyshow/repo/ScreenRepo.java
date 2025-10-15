package com.jayesh.bookmyshow.repo;

import com.jayesh.bookmyshow.entities.Screen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScreenRepo extends JpaRepository<Screen,Long> {
}
