package com.codecrafters.api.hypem;

import java.util.SortedMap;

import static com.google.common.base.Preconditions.checkArgument;

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
    public SortedMap<Integer, HypemSong> getPlaylist(final HypemPlaylistUrl playlist) {
        checkArgument(playlist != null);
        return hypemPlaylistAdapter.getPlaylist(playlist);
    }
}
