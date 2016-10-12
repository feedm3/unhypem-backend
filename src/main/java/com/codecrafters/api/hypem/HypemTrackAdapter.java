package com.codecrafters.api.hypem;

import com.jayway.jsonpath.JsonPath;
import org.apache.commons.lang3.StringUtils;
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

    private static final String SOUNDCLOUD_HOST_NAME = "soundcloud.com";

    // all requests need the same cookie because hypem will generate a key with it
    private static final String HYPEM_AUTH_COOKIE = "AUTH=03:95e416f279a4f69d206c4786c7fb3fd6:1435915799:1527019462:10-DE";

    private static final String HYPEM_TRACK_URL = "http://hypem.com/track/";
    private static final String HYPEM_GO_URL = "http://hypem.com/go/sc/";
    private static final String HYPEM_SERVE_URL = "http://hypem.com/serve/source/";

    private final RestTemplate restTemplate;

    /* package */ HypemTrackAdapter(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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
            if (isSoundcloudUrl(goUrl)) {
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
        final RequestEntity<Void> requestEntity = RequestEntity.head(URI.create(HYPEM_GO_URL + hypemId)).build();
        final ResponseEntity<Void> exchange = restTemplate.exchange(requestEntity, Void.class);
        return exchange.getHeaders().getLocation();
    }

    private String getHostingServeJsonBody(final String hypemId) {
        final String key = getTrackUrlAccessKey(hypemId);
        final RequestEntity<Void> mp3Request = RequestEntity.get(URI.create(HYPEM_SERVE_URL + hypemId + "/" + key)).header("Cookie", HYPEM_AUTH_COOKIE).build();
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
        final RequestEntity<Void> hypemKeyRequest = RequestEntity.get(URI.create(HYPEM_TRACK_URL + hypemId)).header("Cookie", HYPEM_AUTH_COOKIE).build();
        final ResponseEntity<String> hypemKeyResponse = restTemplate.exchange(hypemKeyRequest, String.class);

        final String body = hypemKeyResponse.getBody();
        final String rawSongJson = StringUtils.substringBetween(body, "<script type=\"application/json\" id=\"displayList-data\">", "<script type=\"text/javascript\">");
        final String songJson = StringUtils.trim(rawSongJson);

        return JsonPath.read(songJson, "$.tracks[0].key");
    }

    private Optional<URI> extractUrlField(final String json) {
        final String url = JsonPath.read(json, "$.url");
        return Optional.of(URI.create(url));
    }

    private boolean isSoundcloudUrl(final URI fileUri) {
        return fileUri != null &&
                StringUtils.equals(fileUri.getHost(), SOUNDCLOUD_HOST_NAME) &&
                !StringUtils.equals(fileUri.getPath(), "/not/found");
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
