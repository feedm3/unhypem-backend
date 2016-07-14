package com.codecrafters;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Fabian Dietenberger
 */
public class HypemTrackAdapterTest {

    private static final String HYPEM_URL_SOUNDCLOUD_SONG = "http://hypem.com/track/2c87x";

    private static final String HYPEM_ID_SOUNDCLOUD_SONG = "2c87x";
    private static final String HYPEM_ID_MP3_SONG = "2c2k1";
    private static final String HYPEM_ID_NO_FILE_SONG = "2c96b";
    private static final String HYPEM_ID_FORBIDDEN_SONG = "2ey1t";

    private final HypemTrackAdapter hypemTrackAdapter = new HypemTrackAdapter(new RestTemplate());

    @Test
    public void testConvertTrackUrlToId() {
        final String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl(HYPEM_URL_SOUNDCLOUD_SONG);
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);
    }

    @Test
    public void testGetSoundcloudUrlFromHypemId() {
        final URI fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_SOUNDCLOUD_SONG);
        assertThat(fileUri.getHost()).isEqualTo("soundcloud.com");
    }

    @Test
    public void testGetMp3UrlFromHypemId() {
        final URI fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_MP3_SONG);
        assertThat(fileUri.getPath()).endsWith(".mp3");
    }

}