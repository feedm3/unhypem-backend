package com.codecrafters.jobs;

import com.codecrafters.api.hypem.HypemApi;
import com.codecrafters.api.hypem.HypemPlaylist;
import com.codecrafters.api.hypem.HypemSong;
import com.codecrafters.popular.PopularSongs;
import com.codecrafters.popular.PopularSongsService;
import com.codecrafters.song.Song;
import com.codecrafters.song.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This class is used to import the current popular charts into the database at a given interval.
 */
@Component
class PopularChartsImportJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(PopularChartsImportJob.class);

    private final PopularSongsService popularSongsService;
    private final SongRepository songRepository;
    private final HypemApi hypemApi;

    @Autowired
    public PopularChartsImportJob(final PopularSongsService popularSongsService, final SongRepository songRepository, final HypemApi hypemApi) {
        this.popularSongsService = popularSongsService;
        this.songRepository = songRepository;
        this.hypemApi = hypemApi;
    }

    @Scheduled(fixedRateString = "${unhypem.import.interval-in-millis}")
    public void importCurrentPopularCharts() {
        LOGGER.info("Start importing new popular charts");
        final SortedMap<Integer, HypemSong> popularNowPlaylist = hypemApi.getPlaylist(HypemPlaylist.POPULAR_NOW);

        final SortedMap<Integer, Song> resultCharts = new TreeMap<>();
        popularNowPlaylist.forEach((position, hypemSong) -> {
            final Song song = Song.from(hypemSong);
            final Song savedSong = songRepository.save(song);
            resultCharts.put(position, savedSong);
        });

        final PopularSongs popularSongs = new PopularSongs();
        popularSongs.setSongs(resultCharts);
        popularSongs.setTimestamp(LocalDateTime.now());
        popularSongsService.savePopularSongs(popularSongs);
        LOGGER.info("Finished importing new popular charts");
    }
}
