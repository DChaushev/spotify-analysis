package com.techfit.spotifyanalysis.model;

import java.util.List;

public record ResultItem(List<TrackItem> resultItems, int total) {
}
