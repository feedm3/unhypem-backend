package com.codecrafters.jobs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codecrafters.charts.ChartsService;
import com.codecrafters.song.SongService;

/**
 * This class is used to check database tables dont exceed 10.000 rows
 * to not reach heroku free tier limits.
 */
@Component
public class DatabaseCleaningJob {

    private static final int HEROKU_MAX_DATABASE_ROWS = 10_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseCleaningJob.class);

    private final ChartsService chartsService;

    private final SongService songService;

    @Autowired
    public DatabaseCleaningJob(final ChartsService chartsService, final SongService songService) {
        this.chartsService = chartsService;
        this.songService = songService;
    }

    @Scheduled(fixedRateString = "${unhypem.database-cleaning.interval-in-millis}")
    public void limitDatabaseTablesTo10kRows() {
        LOGGER.info("Checking database table sizes...");
        final long numberOfCharts = chartsService.count();
        final long numberOfSongs = songService.count();

        // divide through 50 as every chart has a mapping table to the songs with 50 rows each
        if (numberOfCharts >= (HEROKU_MAX_DATABASE_ROWS / 50)) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the charts table", numberOfCharts);
            LOGGER.warn("Deleting some old records...");
            chartsService.deleteSomeOfTheOldestRecords();
        }

        if (numberOfSongs >= HEROKU_MAX_DATABASE_ROWS) {
            LOGGER.warn("Heroku free tier database limit nearly reach with {} rows in the songs table", numberOfSongs);
            LOGGER.warn("Deleting some old records...");
            songService.deleteSomeOfTheOldestRecords();
        }
        LOGGER.info("Database check finished");
    }
}
