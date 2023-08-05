package com.techfit.spotifyanalysis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techfit.spotifyanalysis.model.TrackItem;
import com.techfit.spotifyanalysis.repository.TrackItemRepository;

@RestController
@RequestMapping("/tracks")
public class TrackItemController {

    private final TrackItemRepository repository;

    public TrackItemController(TrackItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TrackItem> getAll() {
        return repository.getAll();
    }
}
