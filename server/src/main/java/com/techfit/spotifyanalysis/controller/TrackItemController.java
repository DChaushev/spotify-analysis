package com.techfit.spotifyanalysis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techfit.spotifyanalysis.model.ResultItem;
import com.techfit.spotifyanalysis.repository.TracksRepository;

@RestController
@RequestMapping("/tracks")
public class TrackItemController {

    private final TracksRepository repository;

    public TrackItemController(TracksRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResultItem getTopTracks(@RequestParam Integer limit,
            @RequestParam(required = false) Integer offset) throws Exception {
        return repository.getTopTracks(limit, offset);
    }
}
