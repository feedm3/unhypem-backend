package com.codecrafters.api.hypem;

import com.codecrafters.api.soundcloud.SoundcloudUtils;
import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This class is used to get the hosting URL of a song on hypem. All you need is the hypem id of the song (the id is
 * also used in the URL of the song).
 *
 * @author Fabian Dietenberger
 */
/* package */ class HypemTrackAdapter {

    private final String HYPEM_TRACK_URL = "http://hypem.com/track/";

    private final HypemConfiguration hypemConfiguration;
    private final RestTemplate restTemplate;

    /* package */ HypemTrackAdapter(final RestTemplate restTemplate, final HypemConfiguration configuration) {
        this.restTemplate = restTemplate;
        this.hypemConfiguration = configuration;
        setupRestErrorHandler();
    }

    /**
     * Get the hypem id from a hypem track URL. This function simply extracts the id from the URL.
     * <p>
     * Example: http://hypem.com/track/2c87x will return 2c87x
     *
     * @param hypemTrackUrl the hypem URL to a song
     * @return the media id from the URL or an empty string if the URL is not valid
     * @throws IllegalArgumentException if the hypemTrackUrl is not valid
     */
    /* package */ String getHypemMediaIdFromUrl(final String hypemTrackUrl) {
        checkArgument(hypemTrackUrl != null);
        checkArgument(hypemTrackUrl.startsWith(HYPEM_TRACK_URL));
        checkArgument(hypemTrackUrl.split("/").length >= 5);
        return hypemTrackUrl.split("/")[4];
    }

    /**
     * Get the songs hosting URL from a hypem id. Most of the times it's a soundcloud or mp3 URL.
     *
     * @param hypemId the hypem id to resolve the hosting URL
     * @return the URL to the song or null if the id cannot be resolved
     */
    /* package */ Optional<URI> getFileUriByHypemId(final String hypemId) {
        if (StringUtils.isNotBlank(hypemId)) {
            final URI goUrl = getHostingGoUrl(hypemId);
            if (SoundcloudUtils.isSoundcloudUrl(goUrl)) {
                return Optional.of(goUrl);
            } else {
                // if the song is not hosted on soundcloud we need to request another hypem endpoint
                final String jsonBody = getHostingServeJsonBody(hypemId);
                if (StringUtils.isNotBlank(jsonBody)) {
                    return extractUrlField(jsonBody);
                }
            }
        }
        return Optional.empty();
    }

    private URI getHostingGoUrl(final String hypemId) {
        final String hypemGoUrl = hypemConfiguration.getHypemBaseUrl() +
                "/go/sc/" + hypemId +
                "?key=" + hypemConfiguration.getHypemKey();
        final RequestEntity<Void> requestEntity = HypemRequestEntity.to(HttpMethod.HEAD, hypemGoUrl);
        final ResponseEntity<Void> exchange = restTemplate.exchange(requestEntity, Void.class);
        return exchange.getHeaders().getLocation();
    }

    private String getHostingServeJsonBody(final String hypemId) {
        final String key = getTrackUrlAccessKey(hypemId);
        final String hypemServeUrl = hypemConfiguration.getHypemBaseUrl() +
                "/serve/source/" + hypemId +
                "/" + key +
                "?key=" + hypemConfiguration.getHypemKey();
        final RequestEntity<Void> mp3Request = HypemRequestEntity.to(HttpMethod.GET, hypemServeUrl);
        final ResponseEntity<String> mp3Response = restTemplate.exchange(mp3Request, String.class);

        if (mp3Response.getStatusCode() == HttpStatus.OK) {
            return mp3Response.getBody();
        }
        return "";
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
        final String hypemTrackUrl = hypemConfiguration.getHypemBaseUrl() +
                "/track/" + hypemId +
                "?key=" + hypemConfiguration.getHypemKey();
        final RequestEntity<Void> hypemKeyRequest = HypemRequestEntity.to(HttpMethod.GET, hypemTrackUrl);
        final ResponseEntity<String> hypemKeyResponse = restTemplate.exchange(hypemKeyRequest, String.class);

        final String body = hypemKeyResponse.getBody();
        final String rawSongJson = StringUtils.substringBetween(body, "<script type=\"application/json\" id=\"displayList-data\">", "</script>");
        final String songJson = StringUtils.trim(rawSongJson);

        try {
            return JsonPath.read(songJson, "$.tracks[0].key");
        } catch (final Exception e) {
            return "";
        }
    }

    private Optional<URI> extractUrlField(final String json) {
        final String url = JsonPath.read(json, "$.url");
        return Optional.of(URI.create(url));
    }

    private void setupRestErrorHandler() {
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(final ClientHttpResponse response) throws IOException {
                return false;
            }

            @Override
            public void handleError(final ClientHttpResponse response) throws IOException {

            }
        });
    }
}
