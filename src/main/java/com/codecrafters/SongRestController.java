package com.codecrafters;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Dietenberger
 */
@RestController
public class SongRestController {

    @RequestMapping("/song")
    public Song getRandomSong() {
        final Song song = new Song();
        song.setArtist("Mord Fustang");
        song.setTitle("Drivel");
        return song;
    }
}
