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
    private static final int SONGS_PER_CHART = 50;
    private static final int HEROKU_MAX_DATABASE_ROWS_GAP = 2000;
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
        final long maxNumberOfCharts = HEROKU_MAX_DATABASE_ROWS / SONGS_PER_CHART;
        final long maxNumberOfSongs = HEROKU_MAX_DATABASE_ROWS - HEROKU_MAX_DATABASE_ROWS_GAP;

        LOGGER.info("Number of charts: {}/{}", numberOfCharts, maxNumberOfCharts);
        LOGGER.info("Number of songs: {}/{}", numberOfSongs, maxNumberOfSongs);

        // divide through 50 as every chart has a mapping table to the songs with 50 rows each
        if (numberOfCharts >= maxNumberOfCharts) {
            LOGGER.warn("Maximum number of charts reached, clearing...");
            chartsService.deleteSomeOfTheOldestRecords();
        }

        if (numberOfSongs >= maxNumberOfSongs) {
            LOGGER.warn("Maximum number of songs reached, clearing...");
            songService.deleteSomeOfTheOldestRecords();
        }
        LOGGER.info("Database check finished");
    }
}
