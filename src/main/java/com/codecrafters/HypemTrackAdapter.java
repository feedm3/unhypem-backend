package com.codecrafters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;

/**
 * This class is used to get the hosting URL of a song on hypem. All you need is the hypem id of the song (the id is
 * also used in the URL of the song).
 *
 * @author Fabian Dietenberger
 */
public class HypemTrackAdapter {

    private static final String SOUNDCLOUD_HOST_NAME = "soundcloud.com";

    // all requests need the same cookie because hypem will generate a key with it
    private static final String HYPEM_AUTH_COOKIE = "AUTH=03:95e416f279a4f69d206c4786c7fb3fd6:1435915799:1527019462:10-DE";

    private static final String HYPEM_TRACK_URL = "http://hypem.com/track/";
    private static final String HYPEM_GO_URL = "http://hypem.com/go/sc/";
    private static final String HYPEM_SERVE_URL = "http://hypem.com/serve/source/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public HypemTrackAdapter(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public String getHypemMediaIdFromUrl(final String hypemTrackUrl) {
        final String trimmedUrl = StringUtils.trim(hypemTrackUrl);
        if (StringUtils.startsWith(trimmedUrl, HYPEM_TRACK_URL)) {
            final URI trackUri = URI.create(trimmedUrl);
            final String path = trackUri.getPath();
            final String[] pathParts = path.split("/");
            if (pathParts.length >= 3) {
                final String hypemMediaId = path.split("/")[2];
                return hypemMediaId;
            }
        }
        return "";
    }

    public URI getFileUriByHypemId(final String hypemId) {
        final RequestEntity<Void> requestEntity = RequestEntity.head(URI.create(HYPEM_GO_URL + hypemId)).build();
        final ResponseEntity<Void> exchange = restTemplate.exchange(requestEntity, Void.class);
        final URI fileUri = exchange.getHeaders().getLocation();

        if (isSoundcloudUrl(fileUri)) {
            return fileUri;
        } else {
            final String key = getTrackUrlAccessKey(hypemId);

            final RequestEntity<Void> mp3Request = RequestEntity.get(URI.create(HYPEM_SERVE_URL + hypemId + "/" + key)).header("Cookie", HYPEM_AUTH_COOKIE).build();
            final ResponseEntity<String> mp3Response = restTemplate.exchange(mp3Request, String.class);

            if (mp3Response.getStatusCode() == HttpStatus.OK) {
                final String body = mp3Response.getBody();
                try {
                    final JsonNode mp3ResponseJsonNode = objectMapper.readTree(body);
                    final String url = mp3ResponseJsonNode.get("url").asText();
                    return URI.create(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    // if this exception occurs we have to improve the - vary naive - parsing
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * To get the MP3 URL of a song we first need a key which we can later use
     * to access the URL. The key is inside a response from hypem so we have to
     * parse through this response to find the kay attribute.
     *
     * @param hypemId the hypem id to get the key
     * @return the key
     */
    private String getTrackUrlAccessKey(final String hypemId) {
        final RequestEntity<Void> hypemKeyRequest = RequestEntity.get(URI.create(HYPEM_TRACK_URL + hypemId)).header("Cookie", HYPEM_AUTH_COOKIE).build();
        final ResponseEntity<String> hypemKeyResponse = restTemplate.exchange(hypemKeyRequest, String.class);

        final String body = hypemKeyResponse.getBody();
        final String rawSongJson = StringUtils.substringBetween(body, "<script type=\"application/json\" id=\"displayList-data\">", "<script type=\"text/javascript\">");
        final String songJson = StringUtils.trim(rawSongJson);

        try {
            final JsonNode jsonNode = objectMapper.readTree(songJson);
            return jsonNode.get("tracks").get(0).get("key").asText();
        } catch (IOException e) {
            // if this exception occurs we have to improve the - vary naive - parsing
            e.printStackTrace();
            return "";
        }
    }

    private boolean isSoundcloudUrl(final URI fileUri) {
        return fileUri != null &&
                StringUtils.equals(fileUri.getHost(), SOUNDCLOUD_HOST_NAME) &&
                !StringUtils.equals(fileUri.getPath(), "/not/found");
    }
}
