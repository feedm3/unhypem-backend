package com.codecrafters;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Fabian Dietenberger
 */
public class HypemAdapter {

    private static final String HYPEM_AUTH_COOKIE = "AUTH=03%3A406b2fe38a1ab80a2953869a475ff110%3A1412624464%3A1469266248%3A01-DE";

    private static final String HYPEM_TRACK_URL = "http://hypem.com/track/";
    private static final String HYPEM_GO_URL = "http://hypem.com/go/sc/";
    private static final String HYPEM_SERVE_URL = "http://hypem.com/serve/source/";

    private final RestTemplate restTemplate;

    public HypemAdapter(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public URI getFileUriByHypemId(final String hypemId) {
        final RequestEntity<Void> requestEntity = RequestEntity.head(URI.create(HYPEM_GO_URL + hypemId)).build();
        final ResponseEntity<Void> exchange = restTemplate.exchange(requestEntity, Void.class);
        final URI fileUri = exchange.getHeaders().getLocation();
        return fileUri;
    }
}
