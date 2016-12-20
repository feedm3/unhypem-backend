package com.codecrafters.api.hypem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;
import java.util.SortedMap;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * This class is used to interact with hypem.
 */
@Component
public class HypemApi {

    private final HypemPlaylistAdapter hypemPlaylistAdapter;
    private final HypemTrackAdapter hypemTrackAdapter;

    @Autowired
    public HypemApi(final HypemConfiguration configuration) {
        hypemPlaylistAdapter = new HypemPlaylistAdapter();
        hypemTrackAdapter = new HypemTrackAdapter(new RestTemplate(), configuration);
    }

    /**
     * Get the given playlist. The songs are from position 1 to 50.
     *
     * @param playlistUrl the playlist to get
     * @return a sorted map with the position (starting at 1 up to 50) as key and the song as value
     */
    public SortedMap<Integer, HypemSong> getPlaylist(final HypemPlaylistUrl playlistUrl) {
        checkArgument(playlistUrl != null);
        final SortedMap<Integer, HypemSong> playlist = hypemPlaylistAdapter.getPlaylist(playlistUrl);
        playlist.forEach((position, song) -> {
            final Optional<URI> fileUri = hypemTrackAdapter.getFileUriByHypemId(song.getMediaid());
            fileUri.ifPresent(uri -> song.setFileUrl(uri.toString()));
        });
        return playlist;
    }
}
