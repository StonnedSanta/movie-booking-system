package com.qmovi.controller;

import com.qmovi.dto.MovieDTO;
import com.qmovi.entity.Movie;
import com.qmovi.service.MovieService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public Movie addMovie(@Valid @RequestBody MovieDTO movieDTO) {

        Movie movie = new Movie(
            movieDTO.getName(),
            movieDTO.getGenre(),
            movieDTO.getRating()
        );

        return movieService.addMovie(movie);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @DeleteMapping("/{id}")
    public String deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return "Movie deleted successfully";
    }
}