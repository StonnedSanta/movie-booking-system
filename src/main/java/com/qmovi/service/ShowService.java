package com.qmovi.service;

import java.util.List;

import com.qmovi.entity.Show;

public interface ShowService {

    Show addShow(Show show);

    List<Show> getAllShows();
}