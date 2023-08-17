package com.techfit.spotifyanalysis.repository;

import com.techfit.spotifyanalysis.controller.ResponseItem;

public interface TracksRepository {
    ResponseItem getTopTracks(String accessToken, Integer limit, Integer offset) throws Exception;
}
