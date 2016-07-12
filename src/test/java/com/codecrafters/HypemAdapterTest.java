package com.codecrafters;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * @author Fabian Dietenberger
 */
public class HypemAdapterTest {

    private static final String HYPEM_ID_SOUNDCLOUD_SONG = "2c87x";
    private static final String HYPEM_ID_MP3_SONG = "2c2k1";
    private static final String HYPEM_ID_NO_FILE_SONG = "2c96b";
    private static final String HYPEM_ID_FORBIDDEN_SONG = "2ey1t";


    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getFileUriById() {
        final HypemAdapter hypemService = new HypemAdapter(restTemplate);
        final URI fileUri = hypemService.getFileUriByHypemId(HYPEM_ID_SOUNDCLOUD_SONG);

        Assert.assertNotNull(fileUri);
        Assert.assertEquals("soundcloud.com", fileUri.getHost());
    }

}