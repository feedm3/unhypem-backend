package com.codecrafters.song;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author Fabian Dietenberger
 */
@Entity
public class Song {

    @Id
    @GeneratedValue
    private long id;
    private String artist;
    private String title;
    private int durationInSeconds;
    private String hypemMediaId;
    private long hypemLovedCount;
    private String streamUrl;
    private String soundcloudUrl;
    private String soundcloudId;
    private String waveformUrl;

    public Song() {
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(final String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(final int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public String getHypemMediaId() {
        return hypemMediaId;
    }

    public void setHypemMediaId(final String hypemMediaId) {
        this.hypemMediaId = hypemMediaId;
    }

    public long getHypemLovedCount() {
        return hypemLovedCount;
    }

    public void setHypemLovedCount(final long hypemLovedCount) {
        this.hypemLovedCount = hypemLovedCount;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(final String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getSoundcloudUrl() {
        return soundcloudUrl;
    }

    public void setSoundcloudUrl(final String soundcloudUrl) {
        this.soundcloudUrl = soundcloudUrl;
    }

    public String getSoundcloudId() {
        return soundcloudId;
    }

    public void setSoundcloudId(final String soundcloudId) {
        this.soundcloudId = soundcloudId;
    }

    public String getWaveformUrl() {
        return waveformUrl;
    }

    public void setWaveformUrl(final String waveformUrl) {
        this.waveformUrl = waveformUrl;
    }
}
