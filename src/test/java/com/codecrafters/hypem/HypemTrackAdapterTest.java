package com.codecrafters.hypem;

import com.codecrafters.hypem.HypemTrackAdapter;
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
    public void testConvertUrlToId() {
        final String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl(HYPEM_URL_SOUNDCLOUD_SONG);
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);
    }

    @Test
    public void testConvertUrlToIdShouldIgnoreLeadingAndEndingWhitespace() {
        final String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl("    " + HYPEM_URL_SOUNDCLOUD_SONG + "  ");
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);
    }

    @Test
    public void testConvertUrlToIdWhenUrlIsExtended() {
        final String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl(HYPEM_URL_SOUNDCLOUD_SONG + "/this/is/test");
        assertThat(hypemMediaId).isEqualTo(HYPEM_ID_SOUNDCLOUD_SONG);
    }

    @Test
    public void testConvertUrlToEmptyStringWhenUrlIsInvalid() {
        String hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl("this_is/not.an)valid#url");
        assertThat(hypemMediaId).isEmpty();

        hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl("http://hypem.com/test/?01");
        assertThat(hypemMediaId).isEmpty();

        hypemMediaId = hypemTrackAdapter.getHypemMediaIdFromUrl("http://hypem.com/track/");
        assertThat(hypemMediaId).isEmpty();
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

    @Test
    public void testSongIsNotHosted() {
        final URI fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_NO_FILE_SONG);
        assertThat(fileUri).isNull();
    }

    @Test
    public void testSongHostIsForbidden() {
        final URI fileUri = hypemTrackAdapter.getFileUriByHypemId(HYPEM_ID_FORBIDDEN_SONG);
        assertThat(fileUri).isNull();
    }

    @Test
    public void testHypemIdIsNullOrEmpty() {
        final URI emptyHypemIdUri = hypemTrackAdapter.getFileUriByHypemId("");
        assertThat(emptyHypemIdUri).isNull();

        final URI blankHypemIdUri = hypemTrackAdapter.getFileUriByHypemId("       ");
        assertThat(blankHypemIdUri).isNull();

        final URI nullHypemIdUri = hypemTrackAdapter.getFileUriByHypemId(null);
        assertThat(nullHypemIdUri).isNull();
    }

}