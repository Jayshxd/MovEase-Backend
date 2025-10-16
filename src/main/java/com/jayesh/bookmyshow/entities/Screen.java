package com.jayesh.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "screens")

public class Screen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int totalSeats;

    public Screen(String name, int totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Show> shows = new HashSet<>();


    //if i deleted my screen then all seats will also be removed
    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Seat> seats = new HashSet<>();


    //helper method
    public void addSeat(Seat seat){
        this.seats.add(seat);
        seat.setScreen(this);
    }
    public void removeSeat(Seat seat){
        this.seats.remove(seat);
        seat.setScreen(null);
    }

    public void addShow(Show show){
        this.shows.add(show);
        show.setScreen(this);
    }
    public void removeShow(Show show){
        this.shows.remove(show);
        show.setScreen(null);
    }
}
