package com.codecrafters.charts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface ChartsRepository extends JpaRepository<Charts, Long> {

    /**
     * @return the newest chart entry
     */
    Charts findFirstByOrderByCreatedDateDesc();

    /**
     * @return the 10 oldest chart entries
     */
    List<Charts> findFirst10ByOrderByCreatedDateAsc();
}
