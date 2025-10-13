package com.jayesh.bookmyshow.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//RequiredArgsConstructor
@Entity
@Table(name = "bookings")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalTime bookingTime;
    private double totalAmount;
    private String bookingStatus;

    public Booking(LocalTime bookingTime, double totalAmount, String bookingStatus) {
        this.bookingTime = bookingTime;
        this.totalAmount = totalAmount;
        this.bookingStatus = bookingStatus;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "show_id")
    private Show show;


    @OneToMany(mappedBy = "booking",cascade = CascadeType.ALL)
    private Set<ShowSeat> showSeats;

}
