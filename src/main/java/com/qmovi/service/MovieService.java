package com.qmovi.service;

import com.qmovi.entity.Movie;

import java.util.List;

public interface MovieService {

    Movie addMovie(Movie movie);

    List<Movie> getAllMovies();

    void deleteMovie(Long id);
}