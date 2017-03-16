package com.codecrafters.jobs;

import com.codecrafters.popular.PopularSongs;
import com.codecrafters.popular.PopularSongsRepository;
import com.codecrafters.song.Song;
import com.codecrafters.song.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * This class is used to check database tables dont exceed 10.000 rows
 * to not reach heroku free tier limits.
 */
@Component
public class DatabaseCleaningJob {

    private static final int HEROKU_MAX_DATABASE_ROWS = 10_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaningJob.class);

    private final PopularSongsRepository popularSongsRepository;

    private final SongRepository songRepository;

    @Autowired
    public DatabaseCleaningJob(final PopularSongsRepository popularSongsRepository, final SongRepository songRepository) {
        this.popularSongsRepository = popularSongsRepository;
        this.songRepository = songRepository;
    }

    @Scheduled(fixedRateString = "${unhypem.database-cleaning.interval-in-millis}")
    public void limitDatabaseTablesTo10kRows() {
        LOGGER.info("Checking database table sizes...");
        final long numberOfPopularEntries = popularSongsRepository.count();
        final long numberOfSongs = songRepository.count();

        if (numberOfPopularEntries >= (HEROKU_MAX_DATABASE_ROWS - 1000)) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the popular_songs table", numberOfPopularEntries);
            LOGGER.warn("Deleting some old records...");
            final List<PopularSongs> oldest10Charts = popularSongsRepository.findFirst10ByOrderByCreatedDateAsc();
            popularSongsRepository.deleteInBatch(oldest10Charts);
        }

        if (numberOfSongs >= HEROKU_MAX_DATABASE_ROWS - 1000) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the songs table", numberOfSongs);
            LOGGER.warn("Deleting some old records...");
            final List<Song> oldest100Songs = songRepository.findFirs100ByOrderByCreatedDateAsc();
            songRepository.deleteInBatch(oldest100Songs);
        }
        LOGGER.info("Database check finished");
    }
}
