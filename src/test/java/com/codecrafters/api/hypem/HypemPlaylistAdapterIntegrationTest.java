package com.codecrafters.api.hypem;

import org.junit.Test;

import java.util.SortedMap;

import static com.google.common.truth.Truth.assertThat;


public class HypemPlaylistAdapterIntegrationTest {

    private final HypemPlaylistAdapter chartsAdapter = new HypemPlaylistAdapter();
    private final HypemConfiguration configuration = new HypemConfiguration();

    @Test
    public void testPopularNow() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(new HypemPlaylistUrl(HypemPlaylistUrl.Type.POPULAR_NOW, configuration));
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularLastWeek() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(new HypemPlaylistUrl(HypemPlaylistUrl.Type.POPULAR_LAST_WEEK, configuration));
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularRemixes() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(new HypemPlaylistUrl(HypemPlaylistUrl.Type.POPULAR_REMIXES, configuration));
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }

    @Test
    public void testPopularNoRemixes() {
        final SortedMap<Integer, HypemSong> currentPopularCharts = chartsAdapter.getPlaylist(new HypemPlaylistUrl(HypemPlaylistUrl.Type.POPULAR_NO_REMIXES, configuration));
        assertThat(currentPopularCharts).hasSize(50);
        assertThat(currentPopularCharts.firstKey()).isEqualTo(1);
        assertThat(currentPopularCharts.lastKey()).isEqualTo(50);
    }
}