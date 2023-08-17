package com.techfit.spotifyanalysis.repository;

import com.techfit.spotifyanalysis.controller.ResponseItem;
import com.techfit.spotifyanalysis.model.ResultItem;
import com.techfit.spotifyanalysis.model.TrackItem;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Repository
public class SpotifyApiRepository implements TracksRepository {

    @Override
    public ResponseItem getTopTracks(String accessToken, Integer limit, Integer offset) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet("https://api.spotify.com/v1/me/top/tracks");

        URI uri = new URIBuilder(httpGet.getURI())
            .addParameter("limit", Integer.toString(limit))
            .addParameter("offset", Integer.toString(offset))
            .build();
        httpGet.setURI(uri);
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            return httpClient.execute(httpGet, response -> {
                ResultItem resultItem;
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                    resultItem = convert(jsonObject);
                } else {
                    resultItem = new ResultItem(Collections.emptyList(), 0);
                }
                return new ResponseItem(statusCode, response.getStatusLine().getReasonPhrase(), resultItem);
            });
        }
    }

    private ResultItem convert(JSONObject jsonObject) {
        JSONArray items = jsonObject.getJSONArray("items");
        Iterator<Object> itemsIterator = items.iterator();

        List<TrackItem> tracks = new ArrayList<>();

        while (itemsIterator.hasNext()) {
            JSONObject track = (JSONObject) itemsIterator.next();
            tracks.add(
                new TrackItem(
                    getArtistsNames(track.getJSONArray("artists")),
                    track.getString("name"),
                    track.getJSONObject("album").getString("name"),
                    track.getInt("duration_ms"),
                    track.getJSONObject("album").getJSONArray("images").getJSONObject(1).getString("url"),
                    track.get("preview_url").toString()
                )
            );
        }
        return new ResultItem(tracks, Integer.parseInt(jsonObject.get("total").toString()));
    }

    private String[] getArtistsNames(JSONArray artistsArray) {
        String[] artists = new String[artistsArray.length()];
        for (int i = 0; i < artistsArray.length(); i++) {
            artists[i] = artistsArray.getJSONObject(i).getString("name");
        }
        return artists;
    }
}
