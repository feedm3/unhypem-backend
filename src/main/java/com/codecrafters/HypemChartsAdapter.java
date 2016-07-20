package com.codecrafters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Fabian Dietenberger
 */
public class HypemChartsAdapter {

    private static final String HYPEM_CHARTS_1_TO_20_URL = "http://hypem.com/playlist/popular/3day/json/1/data.js";
    private static final String HYPEM_CHARTS_21_TO_40_URL = "http://hypem.com/playlist/popular/3day/json/2/data.js";
    private static final String HYPEM_CHARTS_41_TO_50_URL = "http://hypem.com/playlist/popular/3day/json/3/data.js";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HypemChartsAdapter(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public List<Song> getCurrentPopularCharts() {
        final List<Song> songs = new ArrayList<>();
        songs.addAll(getSongsFromPlaylistUrl(HYPEM_CHARTS_1_TO_20_URL));
        songs.addAll(getSongsFromPlaylistUrl(HYPEM_CHARTS_21_TO_40_URL));
        songs.addAll(getSongsFromPlaylistUrl(HYPEM_CHARTS_41_TO_50_URL));
        return songs;
    }

    private List<Song> getSongsFromPlaylistUrl(final String playlistUrl) {
        final RequestEntity<Void> playlistRequest = RequestEntity.get(URI.create(playlistUrl)).build();
        final ResponseEntity<String> playlistResponse = restTemplate.exchange(playlistRequest, String.class);

        try {
            final JsonNode songsNode = objectMapper.readTree(playlistResponse.getBody());
            final List<Song> songs = new ArrayList<>();
            for (final JsonNode songNode : songsNode) {
                if (!songNode.has("artist")) {
                    continue;
                }
                final Song song = new Song();
                song.setArtist(songNode.get("artist").asText());
                song.setTitle(songNode.get("title").asText());
                songs.add(song);
            }
            return songs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
