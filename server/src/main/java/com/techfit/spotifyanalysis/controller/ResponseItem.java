package com.techfit.spotifyanalysis.controller;

import com.techfit.spotifyanalysis.model.ResultItem;

public record ResponseItem(int statusCode, String reason, ResultItem resultItem) {
}
