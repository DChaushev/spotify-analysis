package com.techfit.spotifyanalysis.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Repository;

import com.techfit.spotifyanalysis.model.ResultItem;
import com.techfit.spotifyanalysis.model.TrackItem;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.personalization.simplified.GetUsersTopTracksRequest.Builder;

@Repository
public class SpotifyApiRepository implements TracksRepository {

    @Override
    public ResultItem getTopTracks(String accessToken, Integer limit, Integer offset) throws Exception {
        System.out.println("Setting accesToken: " + accessToken);
        SpotifyApi api = new SpotifyApi.Builder()
            .setAccessToken(accessToken)
            .build();
        Builder queryBuilder = api.getUsersTopTracks();
        if (offset != null) {
            //TODO: vadlidate limit and offset
            queryBuilder.offset(offset);
        }
        try {
            Paging<Track> topTracks = queryBuilder
                .limit(limit)
                .build()
                .execute();
            return convert(topTracks);
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            throw e;
        }
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
