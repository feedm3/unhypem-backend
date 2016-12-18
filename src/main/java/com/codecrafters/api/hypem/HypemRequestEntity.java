package com.codecrafters.api.hypem;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;

import java.net.URI;


public class HypemRequestEntity {

    public static RequestEntity<Void> to(final HttpMethod method, final String url) {
        return RequestEntity.method(method, URI.create(url))
                .header("Cookie", "AUTH=03%3A6bf2b1f062d448ef155698c4f7a12c6a%3A1407184615%3A1527014736%3A06-DE")
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .build();
    }
}
