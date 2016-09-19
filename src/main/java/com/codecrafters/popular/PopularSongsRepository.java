package com.codecrafters.popular;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Fabian Dietenberger
 */
@Repository
interface PopularSongsRepository extends JpaRepository<PopularSongs, Long> {

    PopularSongs findFirstByOrderByTimestampAsc();
}
