package com.qmovi.entity;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String genre;

    private double rating;

    @OneToMany(mappedBy = "movie")
    private List<Show> shows;

    public Movie() {

    }

    public Movie(String name, String genre, double rating) {
        this.name = name;
        this.genre = genre;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public double getRating() {
        return rating;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setRating(double rating) {
        if(rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Enter values between 0 to 5");
        }
        this.rating = rating;
    }

}
