package com.codecrafters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * This class is used as adapter for playlists on hypem.
 *
 * @author Fabian Dietenberger
 */
public class HypemPlaylistAdapter {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final String HYPEM_CHARTS_1_TO_20_URL = "http://hypem.com/playlist/popular/3day/json/1";
    private static final String HYPEM_CHARTS_21_TO_40_URL = "http://hypem.com/playlist/popular/3day/json/2";
    private static final String HYPEM_CHARTS_41_TO_50_URL = "http://hypem.com/playlist/popular/3day/json/3";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HypemPlaylistAdapter(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    /**
     *
     * @return
     */
    public SortedMap<Integer, HypemSong> getCurrentPopularCharts() {
        final SortedMap<Integer, HypemSong> songs = new TreeMap<>();
        songs.putAll(getSongsFromPlaylistUrl(1, HYPEM_CHARTS_1_TO_20_URL));
        songs.putAll(getSongsFromPlaylistUrl(21, HYPEM_CHARTS_21_TO_40_URL));
        songs.putAll(getSongsFromPlaylistUrl(41, HYPEM_CHARTS_41_TO_50_URL));
        return songs;
    }

    private SortedMap<Integer, HypemSong> getSongsFromPlaylistUrl(final int positionOffset, final String playlistUrl) {
        final RequestEntity<Void> playlistRequest = RequestEntity.get(URI.create(playlistUrl)).build();
        final ResponseEntity<String> playlistResponse = restTemplate.exchange(playlistRequest, String.class);
        try {
            final JsonNode songsNode = objectMapper.readTree(playlistResponse.getBody());
            final Iterator<Map.Entry<String, JsonNode>> songsFields = songsNode.fields();
            final SortedMap<Integer, HypemSong> songs = new TreeMap<>();
            while (songsFields.hasNext()) {
                final Map.Entry<String, JsonNode> songField = songsFields.next();
                try {
                    final HypemSong song = objectMapper.treeToValue(songField.getValue(), HypemSong.class);
                    final int position = positionOffset + Integer.parseInt(songField.getKey());
                    songs.put(position, song);
                } catch (JsonProcessingException e) {
                    // the json root node contains some meta data which cannot be parsed to a song
                    logger.debug("Cannot convert json node to song", e);
                }
            }
            return songs;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptySortedMap();
        }
    }
}
