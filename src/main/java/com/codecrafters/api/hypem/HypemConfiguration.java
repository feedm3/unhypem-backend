package com.codecrafters.api.hypem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class HypemConfiguration {

    @Value("${hypem.protocol}")
    private String hypemProtocol;

    @Value("${hypem.host}")
    private String hypemHost;

    @Value("${hypem.key}")
    private String hypemKey;

    public String getHypemProtocol() {
        if (hypemProtocol != null) {
            return hypemProtocol;
        }
        return "http";
    }

    public String getHypemHost() {
        if (hypemHost != null) {
            return hypemHost;
        }
        return "hypem.com";
    }

    public String getHypemBaseUrl() {
        return hypemProtocol + "://" + hypemHost;
    }

    public String getHypemKey() {
        return hypemKey;
    }
}
