package com.codecrafters.api.hypem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This class is used as adapter for playlists on hypem.
 *
 * @author Fabian Dietenberger
 */
public class HypemPlaylistAdapter {

    private static final String HYPEM_API_VERSION = "1.1";

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
     * Get the current popular charts from position 1 to 50.
     *
     * @return a sorted map with the position (starting at 1 up to 50) of the position as key and the song as value
     */
    public SortedMap<Integer, HypemSong> getCurrentPopularCharts() {
        final SortedMap<Integer, HypemSong> songs = new TreeMap<>();
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(HYPEM_CHARTS_1_TO_20_URL, 1));
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(HYPEM_CHARTS_21_TO_40_URL, 21));
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(HYPEM_CHARTS_41_TO_50_URL, 41));
        return songs;
    }

    private SortedMap<Integer, HypemSong> getSongsFromPlaylistUrlWithPositionOffset(final String playlistUrl, final int positionOffset) {
        final String playlistJson = requestPlaylistJson(playlistUrl);
        final JsonNode playlistNode = convertJsonToJsonNode(playlistJson);
        if (playlistNode != null) {
            final String version = playlistNode.get("version").asText();
            checkArgument(StringUtils.equals(version, HYPEM_API_VERSION), "Hypem API version has changed. Checkout JSON parser");

            final SortedMap<Integer, HypemSong> songs = new TreeMap<>();

            final Iterator<Map.Entry<String, JsonNode>> songsFields = playlistNode.fields();
            songsFields.forEachRemaining(playlistEntry -> {
                final Pair<Integer, HypemSong> hypemSong = getPositionAndSongFromJson(playlistEntry);
                if (hypemSong != null) {
                    final HypemSong song = hypemSong.getValue();
                    final int position = positionOffset + hypemSong.getKey();
                    songs.put(position, song);
                }
            });
            return songs;
        } else {
            return Collections.emptySortedMap();
        }
    }

    private Pair<Integer, HypemSong> getPositionAndSongFromJson(final Map.Entry<String, JsonNode> positionSongEntry) {
        if (NumberUtils.isNumber(positionSongEntry.getKey())) {
            final HypemSong hypemSong = getSongFromJson(positionSongEntry.getValue().asText());
            final int position = Integer.parseInt(positionSongEntry.getKey());
            return Pair.of(position, hypemSong);
        }
        return null;
    }

    private HypemSong getSongFromJson(final String songJson) {
        return objectMapper.convertValue(songJson, HypemSong.class);
    }

    private String requestPlaylistJson(final String playlistUrl) {
        final RequestEntity<Void> playlistRequest = RequestEntity.get(URI.create(playlistUrl)).build();
        final ResponseEntity<String> playlistResponse = restTemplate.exchange(playlistRequest, String.class);
        return playlistResponse.getBody();
    }

    private JsonNode convertJsonToJsonNode(final String json) {
        try {
            return objectMapper.readTree(json);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
