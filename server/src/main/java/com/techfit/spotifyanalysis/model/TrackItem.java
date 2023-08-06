package com.techfit.spotifyanalysis.model;

public record TrackItem(String[] artists, String name, String album, int duration_ms, String albumCover, String previewUrl) {
}
