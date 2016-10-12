package com.codecrafters.api.hypem;

import org.junit.Test;

import java.util.SortedMap;

import static com.google.common.truth.Truth.assertThat;


public class HypemPlaylistAdapterIntegrationTest {

    private final HypemPlaylistAdapter chartsAdapter = new HypemPlaylistAdapter();

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