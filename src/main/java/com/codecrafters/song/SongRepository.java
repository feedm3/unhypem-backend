package com.codecrafters.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Fabian Dietenberger
 */
@Repository
interface SongRepository extends JpaRepository<Song, Long> {

    Song findOneByHypemMediaId(final String hypemMediaId);

    /**
     * @return the 100 oldest songs
     */
    List<Song> findFirst100ByOrderByCreatedDateAsc();
}
