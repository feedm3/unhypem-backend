package com.codecrafters.api.soundcloud;

import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 *
 */
public class SoundcloudUtils {

    private static final String SOUNDCLOUD_HOST_NAME = "soundcloud.com";

    public static boolean isSoundcloudUrl(final URI url) {
        return url != null &&
                StringUtils.equals(url.getHost(), SOUNDCLOUD_HOST_NAME) &&
                !StringUtils.equals(url.getPath(), "/not/found");
    }
}
