package com.codecrafters.api.hypem;

import java.util.SortedMap;

/**
 * This class is used to interact with hypem.
 */
public class HypemApi {

    private final HypemPlaylistAdapter hypemPlaylistAdapter;

    public HypemApi() {
        hypemPlaylistAdapter = new HypemPlaylistAdapter();
    }

    /**
     * Get the given playlist. The songs are from position 1 to 50.
     *
     * @param playlist the playlist to get
     * @return a sorted map with the position (starting at 1 up to 50) as key and the song as value
     */
    public SortedMap<Integer, HypemSong> getPlaylist(final HypemPlaylist playlist) {
        switch (playlist) {
            case POPULAR_NOW:
                return hypemPlaylistAdapter.getCurrentPopularCharts();
            case POPULAR_LAST_WEEK:
                throw new IllegalStateException("Playlist not implemented yet");
            case POPULAR_REMIXES:
                throw new IllegalStateException("Playlist not implemented yet");
            case POPULAR_NO_REMIXES:
                throw new IllegalStateException("Playlist not implemented yet");
            case POPULAR_ARTISTS:
                throw new IllegalStateException("Playlist not implemented yet");
            default:
                throw new IllegalStateException("Please provide a valid playlist");
        }
    }


}
