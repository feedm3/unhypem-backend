package com.codecrafters.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Dietenberger
 */
@Repository
public interface SongRepository extends JpaRepository<Song, SongId> {

    Song findOneByHypemMediaId(final String hypemMediaId);

    /**
     * @return the 100 oldest songs
     */
    List<Song> findFirs100ByOrderByCreatedDateAsc();
}
