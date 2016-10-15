package com.codecrafters.api.hypem;

import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

/**
 * @author Fabian Dietenberger
 */
public class HypemTrackAdapterIntegrationTest {

    private static final String HYPEM_URL_SOUNDCLOUD_SONG = "http://hypem.com/track/2c87x";

    private static final String HYPEM_ID_SOUNDCLOUD_SONG = "2c87x";
    private static final String HYPEM_ID_MP3_SONG = "2c2k1";
    private static final String HYPEM_ID_NO_FILE_SONG = "2c96b";
    private static final String HYPEM_ID_FORBIDDEN_SONG = "2ey1t";

    private final HypemTrackAdapter hypemTrackAdapter = new HypemTrackAdapter(new RestTemplate());

    @Test
    public void convertUrlToId() {
        String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl(HYPEM_URL_SOUNDCLOUD_SONG);
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);

        hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl(HYPEM_URL_SOUNDCLOUD_SONG + "/this/is/test");
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertUrlToIdShouldThrowExceptionWhenParameterIsSurroundedByWhitespace () {
        hypemTrackAdapter.getHypemMediaIdFromUrl("    " + HYPEM_URL_SOUNDCLOUD_SONG + "  ");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertUrlToIdShouldThrowExceptionWhenParameterIsNotAnUrl() {
        hypemTrackAdapter.getHypemMediaIdFromUrl("this_is/not.an)valid#url");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertUrlToIdShouldThrowExceptionWhenParameterIsNull() {
        hypemTrackAdapter.getHypemMediaIdFromUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertUrlToIdShouldThrowExceptionWhenPathIsInvalid() {
        hypemTrackAdapter.getHypemMediaIdFromUrl("http://hypem.com/test/?01");
    }

    @Test(expected = IllegalArgumentException.class)
    public void convertUrlToIdShouldThrowExceptionWhenPathIsMissing() {
        hypemTrackAdapter.getHypemMediaIdFromUrl("http://hypem.com/track/");
    }

    @Test
    public void testGetSoundcloudUrlFromHypemId() {
        final Optional<URI> fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_SOUNDCLOUD_SONG);
        assertThat(fileUri.get().getHost()).isEqualTo("soundcloud.com");
    }

    @Test
    public void testGetMp3UrlFromHypemId() {
        final Optional<URI> fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_MP3_SONG);
        assertThat(fileUri.get().getPath()).endsWith(".mp3");
    }

    @Test
    public void testSongIsNotHosted() {
        final Optional<URI> fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_NO_FILE_SONG);
        assertThat(fileUri.isPresent()).isFalse();
    }

    @Test
    public void testSongHostIsForbidden() {
        final Optional<URI> fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_FORBIDDEN_SONG);
        assertThat(fileUri.isPresent()).isFalse();
    }

    @Test
    public void testHypemIdIsNullOrEmpty() {
        final Optional<URI> emptyHypemIdUri = hypemTrackAdapter.getFileUriByHypemId("");
        assertThat(emptyHypemIdUri.isPresent()).isFalse();

        final Optional<URI> blankHypemIdUri = hypemTrackAdapter.getFileUriByHypemId("       ");
        assertThat(blankHypemIdUri.isPresent()).isFalse();

        final Optional<URI> nullHypemIdUri = hypemTrackAdapter.getFileUriByHypemId(null);
        assertThat(nullHypemIdUri.isPresent()).isFalse();
    }
}