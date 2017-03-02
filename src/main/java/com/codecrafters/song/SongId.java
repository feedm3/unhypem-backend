package com.codecrafters.song;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Fabian Dietenberger
 */
@Embeddable
class SongId implements Serializable {

    private static final long serialVersionUID = 3071051916048387094L;

    @NotNull
    private String artist;

    @NotNull
    private String title;

    SongId() {
    }

    public SongId(final String artist, final String title) {
        this.artist = artist;
        this.title = title;
    }

    String getArtist() {
        return artist;
    }

    void setArtist(final String artist) {
        this.artist = artist;
    }

    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final SongId songId = (SongId) o;

        if (!artist.equals(songId.artist)) {
            return false;
        }
        return title.equals(songId.title);
    }

    @Override
    public int hashCode() {
        int result = artist.hashCode();
        result = 31 * result + title.hashCode();
        return result;
    }
}
