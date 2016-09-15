package com.codecrafters.song;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Dietenberger
 */
@Repository
public interface SongRepository extends JpaRepository<Song, Long> {

    Song findOneByHypemMediaId(final String hypemMediaId);
}
