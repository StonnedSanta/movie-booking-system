package com.qmovi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qmovi.entity.Show;

public interface ShowRepository extends JpaRepository<Show, Long> {
    
}