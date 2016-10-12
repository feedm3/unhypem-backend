package com.codecrafters.api.hypem;

import org.junit.Test;

import java.util.SortedMap;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Fabian Dietenberger
 */
public class HypemPlaylistAdapterIntegrationTest {

    private final HypemPlaylistAdapter chartsAdapter = new HypemPlaylistAdapter();

    @Test
    public void testCurrentPopularSongs() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getCurrentPopularCharts();
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }


}