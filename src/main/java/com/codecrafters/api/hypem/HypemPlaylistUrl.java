package com.codecrafters.api.hypem;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 */
public class HypemPlaylistUrl {

    public enum Type {
        POPULAR_NOW("3day"),
        POPULAR_LAST_WEEK("lastweek"),
        POPULAR_REMIXES("remix"),
        POPULAR_NO_REMIXES("noremix");

        private final String pathName;

        Type(final String pathName) {
            this.pathName = pathName;
        }

        private String getPathName() {
            return pathName;
        }
    }

    private final String PATH_TEMPLATE_POSITION_1_TO_20 = "/playlist/popular/%s/json/1";
    private final String PATH_TEMPLATE_POSITION_21_TO_40 = "/playlist/popular/%s/json/2";
    private final String PATH_TEMPLATE_POSITION_41_TO_50 = "/playlist/popular/%s/json/3";

    @Value("${hypem.protocol}")
    private String hypemProtocol;

    @Value("${hypem.host}")
    private String hypemHost;

    @Value("${hypem.key}")
    private String hypemKey;

    private final Type type;

    private final String hypemPlaylistUrlPosition1To20;
    private final String hypemPlaylistUrlPosition21To40;
    private final String hypemPlaylistUrlPosition41To50;

    public HypemPlaylistUrl(final Type type) {
        this.type = type;

        hypemPlaylistUrlPosition1To20 = hypemProtocol + "://" + hypemHost + getPathForPosition1To20() + getKeyParam();
        hypemPlaylistUrlPosition21To40 = hypemProtocol + "://" + hypemHost + getPathForPosition21To40() + getKeyParam();
        hypemPlaylistUrlPosition41To50 = hypemProtocol + "://" + hypemHost + getPathForPosition41To50() + getKeyParam();
    }

    /* package */ String getHypemPlaylistUrlPosition1To20() {
        return hypemPlaylistUrlPosition1To20;
    }

    /* package */ String getHypemPlaylistUrlPosition21To40() {
        return hypemPlaylistUrlPosition21To40;
    }

    /* package */ String getHypemPlaylistUrlPosition41To50() {
        return hypemPlaylistUrlPosition41To50;
    }

    private String getPathForPosition1To20() {
        return String.format(PATH_TEMPLATE_POSITION_1_TO_20, type.getPathName());
    }

    private String getPathForPosition21To40() {
        return String.format(PATH_TEMPLATE_POSITION_21_TO_40, type.getPathName());
    }

    private String getPathForPosition41To50() {
        return String.format(PATH_TEMPLATE_POSITION_41_TO_50, type.getPathName());
    }

    private String getKeyParam() {
        if (StringUtils.isNotBlank(hypemKey)) {
            return "?key=" + hypemKey;
        }
        return "";
    }

}
