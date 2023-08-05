package com.techfit.spotifyanalysis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfit.spotifyanalysis.repository.TrackItemRepository;

import se.michaelthelin.spotify.model_objects.specification.Track;

@RestController
@RequestMapping("/tracks")
public class TrackItemController {

    private final TrackItemRepository repository;

    public TrackItemController(TrackItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Track[] getAll() {
        return repository.getAll();
    }
}
