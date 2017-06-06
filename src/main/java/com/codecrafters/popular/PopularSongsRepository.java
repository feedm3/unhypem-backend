package com.codecrafters.popular;

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
}
