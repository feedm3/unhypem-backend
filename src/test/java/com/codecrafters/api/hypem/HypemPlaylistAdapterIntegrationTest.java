package com.codecrafters.api.hypem;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.SortedMap;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Fabian Dietenberger
 */
public class HypemPlaylistAdapterIntegrationTest {

    private final HypemPlaylistAdapter chartsAdapter = new HypemPlaylistAdapter(new RestTemplate());

    @Test
    public void testCurrentPopularSongs() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getCurrentPopularCharts();
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }



}