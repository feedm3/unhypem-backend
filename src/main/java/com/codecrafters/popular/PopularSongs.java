package com.codecrafters.popular;

import com.codecrafters.song.Song;
import org.hibernate.annotations.SortNatural;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.SortedMap;

/**
 * @author Fabian Dietenberger
 */
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PopularSongs {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @SortNatural
    private SortedMap<Integer, Song> songs;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }
}
