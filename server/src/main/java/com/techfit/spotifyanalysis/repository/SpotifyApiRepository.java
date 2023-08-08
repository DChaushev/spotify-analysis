package com.techfit.spotifyanalysis.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.techfit.spotifyanalysis.model.ResultItem;
import com.techfit.spotifyanalysis.model.TrackItem;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Repository
public class SpotifyApiRepository implements TracksRepository {

    @Override
    public ResultItem getTopTracks(String accessToken, Integer limit, Integer offset) throws Exception {
        SpotifyApi api = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
        Paging<Track> topTracks = api.getUsersTopTracks()
            .limit(limit)
            .offset(offset)
            .build()
            .execute();
        return convert(topTracks);
    }

    private ResultItem convert(Paging<Track> topTracks) {
        List<TrackItem> tracks = new ArrayList<>();
        
        Track[] items = topTracks.getItems();
        for (Track track : items) {
            tracks.add(
                new TrackItem(
                    getArtistsNames(track.getArtists()),
                    track.getName(),
                    track.getAlbum().getName(),
                    track.getDurationMs(),
                    track.getAlbum() != null ? track.getAlbum().getImages()[0].getUrl() : "",
                    track.getPreviewUrl()
                )
            );
        }
        return new ResultItem(tracks, topTracks.getTotal(), 
                    topTracks.getPrevious(),
                    topTracks.getNext());
    }

    private String[] getArtistsNames(ArtistSimplified[] artistSimplified) {
        String[] artists = new String[artistSimplified.length];
        for (int i = 0; i < artistSimplified.length; i++) {
            artists[i] = artistSimplified[i].getName();
        }
        return artists;
    }
}
