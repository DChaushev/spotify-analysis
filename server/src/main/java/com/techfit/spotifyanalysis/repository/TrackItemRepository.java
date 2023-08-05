package com.techfit.spotifyanalysis.repository;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Repository;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Repository
public class TrackItemRepository {
    private static final String accessToken = "<access-token>";

    private final SpotifyApi api = new SpotifyApi.Builder().setAccessToken(accessToken).build();

    public Track[] getAll() {
        try {
            Track[] topTracks = api.getUsersTopTracks().build().execute().getItems();
            return topTracks;
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }
        return new Track[]{};
    }
}
