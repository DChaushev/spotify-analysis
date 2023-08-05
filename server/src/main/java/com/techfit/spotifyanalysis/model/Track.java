package com.techfit.spotifyanalysis.model;

public record Track(String[] artists, String name, String album, int durationMs) {
}
