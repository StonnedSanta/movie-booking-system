package com.qmovi.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String showTime;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "show")
    private List<Booking> bookings;

    public Show() {

    }

    public Show(String showTime, Movie movie) {
        this.showTime = showTime;
        this.movie = movie;
    }

    public Long getId() {
        return id;
    }

    public String getShowTime() {
        return showTime;
    }

    public Movie movie() {
        return movie;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}