package com.codecrafters.api.hypem;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
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
/* package */ class HypemPlaylistAdapter {

    private static final String HYPEM_API_VERSION = "1.1";
    private static final Logger LOG = LoggerFactory.getLogger(HypemPlaylistAdapter.class);

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    HypemPlaylistAdapter() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Get the current popular charts from position 1 to 50.
     *
     * @return a sorted map with the position (starting at 1 up to 50) as key and the song as value
     */
    /* package */ SortedMap<Integer, HypemSong> getPlaylist(final HypemPlaylist playlist) {
        final SortedMap<Integer, HypemSong> songs = new TreeMap<>();
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(playlist.getUrlForPosition1To20(), 1));
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(playlist.getUrlForPosition21To40(), 21));
        songs.putAll(getSongsFromPlaylistUrlWithPositionOffset(playlist.getUrlForPosition41To50(), 41));
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
            final HypemSong hypemSong = getSongFromJson(positionSongEntry.getValue().toString());
            final int position = Integer.parseInt(positionSongEntry.getKey());
            return Pair.of(position, hypemSong);
        }
        return null;
    }

    private HypemSong getSongFromJson(final String songJson) {
        try {
            return objectMapper.readValue(songJson, HypemSong.class);
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String requestPlaylistJson(final String playlistUrl) {
        final RequestEntity<Void> playlistRequest = RequestEntity.get(URI.create(playlistUrl)).build();
        try {
            final ResponseEntity<String> playlistResponse = restTemplate.exchange(playlistRequest, String.class);
            return playlistResponse.getBody();
        } catch (final HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), "Could not request " + playlistRequest.getUrl() +
                    ". Headers: " + playlistRequest.getHeaders() + ". " + e.getMessage());
        }
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
