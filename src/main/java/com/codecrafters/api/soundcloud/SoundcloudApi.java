package com.codecrafters.api.soundcloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


@Component
public class SoundcloudApi {

    private final Logger LOG = LoggerFactory.getLogger(SoundcloudApi.class);

    private final SoundcloudConfiguration configuration;
    private final RestTemplate restTemplate;

    @Autowired
    public SoundcloudApi(final SoundcloudConfiguration configuration, final RestTemplate restTemplate) {
        this.configuration = configuration;
        this.restTemplate = restTemplate;
    }

    public Optional<SoundcloudApiResponse> fetchSongData(final String soundcloudUrl) {
        final String fetchUrl = "https://api.soundcloud.com/resolve.json?url=" + soundcloudUrl + "&client_id=" + configuration.getClientId();
        try {
            final SoundcloudApiResponse response = restTemplate.getForObject(fetchUrl, SoundcloudApiResponse.class);
            response.setStreamUrl(response.getStreamUrl() + "?client_id=" + configuration.getClientId());
            return Optional.of(response);
        } catch (final Exception e) {
            LOG.warn("Soundcloud Url could not be fetched. Url: {} - Error: {}", soundcloudUrl, e.getMessage());
            return Optional.empty();
        }
    }
}
