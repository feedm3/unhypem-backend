package com.codecrafters;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Fabian Dietenberger
 */
public class HypemChartsAdapterTest {

    private final HypemChartsAdapter chartsAdapter = new HypemChartsAdapter(new RestTemplate());

    @Test
    public void testCurrentPopularSongs() {
        final List<Song> currentPopularCharts = chartsAdapter.getCurrentPopularCharts();

        assertThat(currentPopularCharts).hasSize(50);
    }

}