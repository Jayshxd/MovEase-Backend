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
//@RequiredArgsConstructor
@Entity
@Table(name = "shows")

public class Show {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate showDate;
    private LocalTime showTime;
    private Double ticketPrice;

    public Show(LocalDate showDate, LocalTime showTime, Double ticketPrice) {
        this.showDate = showDate;
        this.showTime = showTime;
        this.ticketPrice = ticketPrice;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private Set<Booking> bookings;


    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    private Set<ShowSeat> showSeats;

    //helper
    public void addShowSeat(ShowSeat showSeat) {
        this.showSeats.add(showSeat);
        showSeat.setShow(this);
    }
    public void removeShowSeat(ShowSeat showSeat) {
        this.showSeats.remove(showSeat);
        showSeat.setShow(null);
    }
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
        booking.setShow(this);
    }
    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
        booking.setShow(null);
    }


}
