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

    private static final int SONGS_PER_CHART = 50;

    // overall heroku maximum database rows is 10.000 - as we leave some gap to prevent
    // warning mails we just go to maximum 8000 rows
    private static final int HEROKU_MAX_SONGS = 5000;
    private static final int HEROKU_MAX_CHARTS = 3000 / SONGS_PER_CHART;

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

        LOGGER.info("Number of charts: {}/{}", numberOfCharts, HEROKU_MAX_CHARTS);
        LOGGER.info("Number of songs: {}/{}", numberOfSongs, HEROKU_MAX_SONGS);

        // divide through 50 as every chart has a mapping table to the songs with 50 rows each
        if (numberOfCharts >= HEROKU_MAX_CHARTS) {
            LOGGER.warn("Maximum number of charts reached, clearing...");
            chartsService.deleteSomeOfTheOldestRecords();
        }

        if (numberOfSongs >= HEROKU_MAX_SONGS) {
            LOGGER.warn("Maximum number of songs reached, clearing...");
            songService.deleteSomeOfTheOldestRecords();
        }
        LOGGER.info("Database check finished");
    }
}
