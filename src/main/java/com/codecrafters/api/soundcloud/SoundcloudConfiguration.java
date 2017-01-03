package com.codecrafters.api.soundcloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class SoundcloudConfiguration {

    @Value("${soundcloud.clientId}")
    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }
}
