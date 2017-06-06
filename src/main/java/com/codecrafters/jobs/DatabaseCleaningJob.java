package com.codecrafters.jobs;

import com.codecrafters.popular.PopularSongsService;
import com.codecrafters.song.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This class is used to check database tables dont exceed 10.000 rows
 * to not reach heroku free tier limits.
 */
@Component
public class DatabaseCleaningJob {

    private static final int HEROKU_MAX_DATABASE_ROWS = 10_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaningJob.class);

    private final PopularSongsService popularSongsService;

    private final SongService songService;

    @Autowired
    public DatabaseCleaningJob(final PopularSongsService popularSongsService, final SongService songService) {
        this.popularSongsService = popularSongsService;
        this.songService = songService;
    }

    @Scheduled(fixedRateString = "${unhypem.database-cleaning.interval-in-millis}")
    public void limitDatabaseTablesTo10kRows() {
        LOGGER.info("Checking database table sizes...");
        final long numberOfPopularEntries = popularSongsService.count();
        final long numberOfSongs = songService.count();

        if (numberOfPopularEntries >= (HEROKU_MAX_DATABASE_ROWS - 1000)) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the popular_songs table", numberOfPopularEntries);
            LOGGER.warn("Deleting some old records...");
            popularSongsService.deleteSomeOfTheOldestRecords();
        }

        if (numberOfSongs >= HEROKU_MAX_DATABASE_ROWS - 1000) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the songs table", numberOfSongs);
            LOGGER.warn("Deleting some old records...");
            songService.deleteSomeOfTheOldestRecords();
        }
        LOGGER.info("Database check finished");
    }
}
