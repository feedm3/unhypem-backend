package com.codecrafters.api.hypem;

/**
 *
 */
public enum HypemPlaylist {

    POPULAR_NOW("3day"),
    POPULAR_LAST_WEEK("lastweek"),
    POPULAR_REMIXES("remix"),
    POPULAR_NO_REMIXES("noremix");

    private static final String POSITION_1_TO_20_URL_FORMAT = "http://138.68.75.60/hypem/playlist/popular/%s/json/1";
    private static final String POSITION_21_TO_40_URL_FORMAT = "http://138.68.75.60/hypem/playlist/popular/%s/json/2";
    private static final String POSITION_41_TO_50_URL_FORMAT = "http://138.68.75.60/hypem/playlist/popular/%s/json/3";

    private final String pathName;

    /* package */ HypemPlaylist(final String pathName) {
        this.pathName = pathName;
    }

    private String getPathName() {
        return pathName;
    }

    /* package */ String getUrlForPosition1To20() {
        return String.format(POSITION_1_TO_20_URL_FORMAT, pathName);
    }

    /* package */ String getUrlForPosition21To40() {
        return String.format(POSITION_21_TO_40_URL_FORMAT, pathName);
    }

    /* package */ String getUrlForPosition41To50() {
        return String.format(POSITION_41_TO_50_URL_FORMAT, pathName);
    }
}
