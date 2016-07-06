package com.codecrafters;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Fabian Dietenberger
 */
@Entity
public class Song {

    @Id
    private long id;
    private String artist;
    private String title;

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
}
