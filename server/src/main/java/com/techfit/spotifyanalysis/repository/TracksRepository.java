package com.techfit.spotifyanalysis.repository;

import com.techfit.spotifyanalysis.model.ResultItem;

public interface TracksRepository {
    ResultItem getTopTracks(String accessToken, Integer limit, Integer offset) throws Exception;
}
