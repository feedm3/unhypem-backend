package com.codecrafters.jobs;

import com.codecrafters.api.hypem.HypemApi;
import com.codecrafters.api.hypem.HypemConfiguration;
import com.codecrafters.api.hypem.HypemPlaylistUrl;
import com.codecrafters.api.hypem.HypemSong;
import com.codecrafters.api.soundcloud.SoundcloudApi;
import com.codecrafters.api.soundcloud.SoundcloudApiResponse;
import com.codecrafters.popular.PopularSongs;
import com.codecrafters.popular.PopularSongsService;
import com.codecrafters.song.Song;
import com.codecrafters.song.SongService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class is used to import the current popular charts into the database at a given interval.
 */
@Component
class PopularChartsImportJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopularChartsImportJob.class);

    private final PopularSongsService popularSongsService;
    private final SongService songService;
    private final HypemApi hypemApi;
    private final HypemConfiguration hypemConfiguration;
    private final SoundcloudApi soundcloudApi;

    @Autowired
    public PopularChartsImportJob(final PopularSongsService popularSongsService, final SongService songService, final HypemApi hypemApi, final HypemConfiguration hypemConfiguration, final SoundcloudApi soundcloudApi) {
        this.popularSongsService = popularSongsService;
        this.songService = songService;
        this.hypemApi = hypemApi;
        this.hypemConfiguration = hypemConfiguration;
        this.soundcloudApi = soundcloudApi;
    }

    @Scheduled(fixedRateString = "${unhypem.import.interval-in-millis}")
    public void importCurrentPopularCharts() {
        LOGGER.info("Start importing new popular charts...");
        final SortedMap<Integer, HypemSong> popularNowPlaylist = hypemApi.getPlaylist(new HypemPlaylistUrl(HypemPlaylistUrl.Type.POPULAR_NOW, hypemConfiguration));

        // save every single song
        final SortedMap<Integer, Song> resultCharts = new TreeMap<>();
        popularNowPlaylist.forEach((position, hypemSong) -> {
            final Song song = Song.from(hypemSong);
            fetchSoundcloudData(song);
            final Song savedSong = songService.save(song);
            resultCharts.put(position, savedSong);
        });

        // save charts
        final PopularSongs popularSongs = new PopularSongs();
        popularSongs.setSongs(resultCharts);
        popularSongsService.savePopularSongs(popularSongs);
        LOGGER.info("Finished importing new popular charts");
    }

    private void fetchSoundcloudData(final Song song) {
        if (StringUtils.isNotBlank(song.getSoundcloudUrl())) {
            final Optional<SoundcloudApiResponse> soundcloudApiResponse = soundcloudApi.fetchSongData(song.getSoundcloudUrl());
            if (soundcloudApiResponse.isPresent()) {
                song.setSoundcloudId(soundcloudApiResponse.get().getId());
                song.setStreamUrl(soundcloudApiResponse.get().getStreamUrl());
                song.setWaveformUrl(soundcloudApiResponse.get().getWaveformUrl());
                song.setDurationInSeconds(soundcloudApiResponse.get().getDurationInMillis() / 1000);
            }
        }
    }
}
