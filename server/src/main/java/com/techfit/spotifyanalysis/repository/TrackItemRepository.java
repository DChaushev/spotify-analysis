package com.techfit.spotifyanalysis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.techfit.spotifyanalysis.model.Track;
import com.techfit.spotifyanalysis.model.TrackItem;

@Repository
public class TrackItemRepository {
    private final List<TrackItem> trackItems = new ArrayList<>();

    public TrackItemRepository() {
        trackItems.add(
            new TrackItem(
                new Track(new String[] {"Eminem"},
                "Loose Youself",
                "8-th Mile",
                300_000),
            "some-time"));

        trackItems.add(
            new TrackItem(
                new Track(new String[] {"Linkin Park"},
                "In the end",
                "Hybrid theory",
                300_000),
            "some-time"));
        
        trackItems.add(
            new TrackItem(
                new Track(new String[] {"Disturbed"},
                "Into the fire",
                "Sons of plunder",
                300_000),
            "some-time"));
    }

    public List<TrackItem> getAll() {
        return trackItems;
    }
}
