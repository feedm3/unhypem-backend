package com.codecrafters.song;

import com.codecrafters.api.hypem.HypemSong;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author Fabian Dietenberger
 */
@Entity
@Table(indexes = {@Index(name = "hypemMediaIdIndex", columnList = "hypemMediaId")})
@EntityListeners(AuditingEntityListener.class)
public class Song {

    @EmbeddedId
    private final SongId songId;

    @Column(unique = true)
    private String hypemMediaId;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date createdDate;

    private int durationInSeconds;
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songId=" + songId +
                ", hypemMediaId='" + hypemMediaId + '\'' +
                ", createdDate=" + createdDate +
                ", durationInSeconds=" + durationInSeconds +
                ", hypemLovedCount=" + hypemLovedCount +
                ", streamUrl='" + streamUrl + '\'' +
                ", soundcloudUrl='" + soundcloudUrl + '\'' +
                ", soundcloudId='" + soundcloudId + '\'' +
                ", waveformUrl='" + waveformUrl + '\'' +
                '}';
    }
}
