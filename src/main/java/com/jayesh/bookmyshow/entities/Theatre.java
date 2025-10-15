package com.jayesh.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "theatres")
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private String address;

    public Theatre(String name, String city, String address) {
        this.name = name;
        this.city = city;
        this.address = address;
    }

    @OneToMany(mappedBy = "theatre",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Screen> screens = new HashSet<>();

    //helper Methods
    public void addScreen(Screen screen) {
        this.screens.add(screen);
        screen.setTheatre(this);
    }
    public void removeScreen(Screen screen) {
        screens.remove(screen);
        screen.setTheatre(null);
    }
}
