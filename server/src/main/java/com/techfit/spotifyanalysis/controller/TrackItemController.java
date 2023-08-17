package com.techfit.spotifyanalysis.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techfit.spotifyanalysis.repository.TracksRepository;

@RestController
@RequestMapping("/toptracks")
public class TrackItemController {

    private final TracksRepository repository;

    public TrackItemController(TracksRepository repository) {
        this.repository = repository;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping
    public ResponseItem getTopTracks(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestParam Integer limit,
            @RequestParam(required = false) Integer offset) throws Exception {
        String accessToken = getAccessToken(authorization);
        return repository.getTopTracks(accessToken, limit, offset);
    }

    private String getAccessToken(String header) {
        return header.replace("Bearer ", "");
    }
}
