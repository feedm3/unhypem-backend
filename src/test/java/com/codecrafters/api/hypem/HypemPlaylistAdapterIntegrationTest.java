package com.codecrafters.api.hypem;

import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.SortedMap;

import static com.google.common.truth.Truth.assertThat;


public class HypemPlaylistAdapterIntegrationTest {

    private final HypemPlaylistAdapter chartsAdapter = new HypemPlaylistAdapter();

    @Test
    public void requestGoogle() {
        final RestTemplate restTemplate = new RestTemplate();

        final RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, URI.create("http://google.com"));
        final ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

        assertThat(exchange.getStatusCode()).isEquivalentAccordingToCompareTo(HttpStatus.OK);
    }


    @Test
    public void requetsCharts() {
        final RestTemplate restTemplate = new RestTemplate();

        final RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, URI.create("http://hypem.com/playlist/popular/3day/json/1"));
        final ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

        assertThat(exchange.getStatusCode()).isEquivalentAccordingToCompareTo(HttpStatus.OK);
    }

    @Test
    public void requestHypem() {
        final RestTemplate restTemplate = new RestTemplate();

        final RequestEntity requestEntity = new RequestEntity(HttpMethod.GET, URI.create("http://hypem.com"));
        final ResponseEntity<String> exchange = restTemplate.exchange(requestEntity, String.class);

        assertThat(exchange.getStatusCode()).isEquivalentAccordingToCompareTo(HttpStatus.OK);
    }

    @Test
    public void testPopularNow() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(HypemPlaylist.POPULAR_NOW);
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularLastWeek() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(HypemPlaylist.POPULAR_LAST_WEEK);
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularRemixes() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(HypemPlaylist.POPULAR_REMIXES);
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularNoRemixes() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(HypemPlaylist.POPULAR_NO_REMIXES);
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }
}