package com.codecrafters.popular;

import com.codecrafters.song.SongId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PopularSongsRepository extends JpaRepository<PopularSongs, Long> {

    /**
     * @return the newest chart entry
     */
    PopularSongs findFirstByOrderByCreatedDateDesc();

    /**
     * @return the 10 oldest chart entries
     */
    List<PopularSongs> findFirst10ByOrderByCreatedDateAsc();

    /**
     * @return all chart entries which contain the given id
     */
    List<PopularSongs> findBySongs_songId(final SongId id);
}
