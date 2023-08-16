package com.techfit.spotifyanalysis.repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Repository;

import com.techfit.spotifyanalysis.model.ResultItem;
import com.techfit.spotifyanalysis.model.TrackItem;


@Repository
public class SpotifyApiRepository implements TracksRepository {

    @Override
    public ResultItem getTopTracks(String accessToken, Integer limit, Integer offset) throws URISyntaxException, IOException {
        HttpGet httpGet = new HttpGet("https://api.spotify.com/v1/me/top/tracks");
        
        URI uri = new URIBuilder(httpGet.getURI())
            .addParameter("limit", Integer.toString(limit))
            .addParameter("offset", Integer.toString(offset))
            .build();
        httpGet.setURI(uri);
        httpGet.setHeader("Authorization", "Bearer " + accessToken);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ResultItem result = httpClient.execute(httpGet, new ResponseHandler<ResultItem>() {

                @Override
                public ResultItem handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    int statusCode = response.getStatusLine().getStatusCode();
                    if (statusCode == HttpStatus.SC_OK) {
                        JSONObject jsonObject = new JSONObject(EntityUtils.toString(response.getEntity()));
                        
                        return convert(jsonObject);
                    }
                    return null;
                }
                
            });

            return result;
        }

    }

    private ResultItem convert(JSONObject jsonObject) {
        JSONArray items = (JSONArray) jsonObject.get("items");
        Iterator<Object> itemsIterator = items.iterator();

        List<TrackItem> tracks = new ArrayList<>();

        while (itemsIterator.hasNext()) {
            JSONObject track = (JSONObject) itemsIterator.next();
            tracks.add(
                new TrackItem(
                    getArtistsNames((JSONArray) track.get("artists")),
                    track.get("name").toString(),
                    ((JSONObject)track.get("album")).get("name").toString(),
                    Integer.valueOf(track.get("duration_ms").toString()),
                    ((JSONObject)((JSONArray) ((JSONObject) track.get("album")).get("images")).get(1)).get("url").toString(),
                    track.get("preview_url").toString()
                )
            );
        }
        return new ResultItem(tracks, Integer.valueOf(jsonObject.get("total").toString()));
    }

    private String[] getArtistsNames(JSONArray artistsArray) {
        String[] artists = new String[artistsArray.length()];
        for (int i = 0; i < artistsArray.length(); i++) {
            artists[i] = ((JSONObject) artistsArray.get(i)).get("name").toString();
        }
        return artists;
    }
}
