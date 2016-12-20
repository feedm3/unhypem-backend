package com.codecrafters.song;

import com.codecrafters.api.hypem.HypemSong;

import javax.persistence.*;

/**
 * @author Fabian Dietenberger
 */
@Entity
@Table(indexes = {@Index(name = "hypemMediaIdIndex", columnList = "hypemMediaId")})
public class Song {

    @EmbeddedId
    private final SongId songId;

    private int durationInSeconds;

    @Column(unique = true)
    private String hypemMediaId;

    private long hypemLovedCount;
    private String streamUrl;
    private String soundcloudUrl;
    private String soundcloudId;
    private String waveformUrl;

    public Song() {
        songId = new SongId();
    }

    public static Song from(final HypemSong hypemSong) {
        final Song song = new Song();
        song.setArtist(hypemSong.getArtist());
        song.setTitle(hypemSong.getTitle());
        song.setHypemMediaId(hypemSong.getMediaid());

        if (hypemSong.isHostedOnSoundcloud()) {
            song.setSoundcloudUrl(hypemSong.getFileUrl());
        } else {
            song.setStreamUrl(hypemSong.getFileUrl());
        }
        song.setHypemLovedCount(hypemSong.getLovedCount());
        return song;
    }

    public String getArtist() {
        return songId.getArtist();
    }

    public void setArtist(final String artist) {
        songId.setArtist(artist);
    }

    public String getTitle() {
        return songId.getTitle();
    }

    public void setTitle(final String title) {
        songId.setTitle(title);
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

    /**
     * The URL to use for streaming. Either a soundcloud stream URL or a direct link to a mp3 file.
     *
     * @param streamUrl the url to set
     */
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
