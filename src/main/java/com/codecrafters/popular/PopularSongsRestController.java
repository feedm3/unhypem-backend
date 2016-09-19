package com.codecrafters.popular;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PopularSongsRestController {

    private final PopularSongsService popularSongsService;

    @Autowired
    public PopularSongsRestController(final PopularSongsService popularSongsService) {
        this.popularSongsService = popularSongsService;
    }

    @RequestMapping("/popular")
    public PopularSongs getPopularSongs() {
        return popularSongsService.getPopularSongs();
    }
}
