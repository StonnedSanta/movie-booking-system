package com.qmovi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieDTO {

    @NotBlank(message = "Movie name cannot be empty")
    private String name;

    @NotBlank(message = "Genre cannot be empty")
    private String genre;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be atleast 1")
    @Max(value = 5, message = "Rating must be atmost 5")
    private Double rating;

    public MovieDTO() {
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public Double getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}