package com.qmovi.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.qmovi.entity.Show;
import com.qmovi.repository.ShowRepository;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Show addShow(Show show) {
        return showRepository.save(show);
    }

    @Override
    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

}