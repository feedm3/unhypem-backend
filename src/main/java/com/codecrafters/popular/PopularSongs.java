package com.codecrafters.popular;

import com.codecrafters.song.Song;
import org.hibernate.annotations.SortNatural;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.SortedMap;

/**
 * @author Fabian Dietenberger
 */
@Entity
public class PopularSongs {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    private SortedMap<Integer, Song> songs;
    private LocalDateTime timestamp;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public SortedMap<Integer, Song> getSongs() {
        return songs;
    }

    public void setSongs(final SortedMap<Integer, Song> songs) {
        this.songs = songs;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
