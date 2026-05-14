package com.qmovi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShowDTO {
    
    @NotBlank(message = "Show time cannot be empty")
    private String showTime;
    
    @NotNull(message = "Movie ID is required")
    private Long movieId;

    public ShowDTO() {

    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
    
    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}