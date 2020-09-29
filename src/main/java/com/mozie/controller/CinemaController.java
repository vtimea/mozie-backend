package com.mozie.controller;

import com.mozie.model.database.Cinema;
import com.mozie.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CinemaController {
    @Autowired
    CinemaRepository cinemaRepository;

    @GetMapping("/cinemas")
    public ResponseEntity<List<Cinema>> getAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return new ResponseEntity<>(cinemas, HttpStatus.OK);
    }
}