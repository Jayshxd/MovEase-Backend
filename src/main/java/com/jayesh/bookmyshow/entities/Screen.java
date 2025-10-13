package com.jayesh.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

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

    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL)
    private Set<Show> shows;

    @OneToMany(mappedBy = "screen",cascade = CascadeType.ALL)
    private Set<Seat> seats;
}
