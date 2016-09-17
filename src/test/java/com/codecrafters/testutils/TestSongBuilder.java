package com.codecrafters.testutils;

import com.codecrafters.song.Song;

/**
 * @author Fabian Dietenberger
 */
public class TestSongBuilder {

    public static final String ARTIST = "Artist";
    public static final String TITLE = "Title";
    public static final String HYPEM_MEDIA_ID = "mediaId";

    public static Song getSong() {
        final Song song = new Song();
        song.setArtist(ARTIST);
        song.setTitle(TITLE);
        song.setHypemMediaId(HYPEM_MEDIA_ID);
        return song;
    }
}
