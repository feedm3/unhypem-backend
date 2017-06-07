package com.codecrafters.jobs;

import com.codecrafters.api.hypem.HypemApi;
import com.codecrafters.api.hypem.HypemConfiguration;
import com.codecrafters.api.hypem.HypemPlaylistUrl;
import com.codecrafters.api.hypem.HypemSong;
import com.codecrafters.api.soundcloud.SoundcloudApi;
import com.codecrafters.api.soundcloud.SoundcloudApiResponse;
import com.codecrafters.charts.Charts;
import com.codecrafters.charts.ChartsService;
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
 * This class is used to import the current charts charts into the database at a given interval.
 */
@Component
class ChartsImportJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChartsImportJob.class);

    private final ChartsService chartsService;
    private final SongService songService;
    private final HypemApi hypemApi;
    private final HypemConfiguration hypemConfiguration;
    private final SoundcloudApi soundcloudApi;

    @Autowired
    public ChartsImportJob(final ChartsService chartsService, final SongService songService, final HypemApi hypemApi, final HypemConfiguration hypemConfiguration, final SoundcloudApi soundcloudApi) {
        this.chartsService = chartsService;
        this.songService = songService;
        this.hypemApi = hypemApi;
        this.hypemConfiguration = hypemConfiguration;
        this.soundcloudApi = soundcloudApi;
    }

    @Scheduled(fixedRateString = "${unhypem.import.interval-in-millis}")
    public void importCurrentCharts() {
        LOGGER.info("Start importing new charts...");
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
        final Charts charts = new Charts();
        charts.setSongs(resultCharts);
        chartsService.saveCharts(charts);
        LOGGER.info("Finished importing new charts");
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
