package com.codecrafters.popular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Dietenberger
 */
@RestController
public class PopularSongsRestController {

    private final PopularSongsRepository popularSongsRepository;

    @Autowired
    public PopularSongsRestController(final PopularSongsRepository popularSongsRepository) {
        this.popularSongsRepository = popularSongsRepository;
    }

    @RequestMapping("/popular")
    public PopularSongs getPopularSongs() {
        return popularSongsRepository.findFirstByOrderByTimestampAsc();
    }
}
