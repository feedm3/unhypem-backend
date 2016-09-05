package com.codecrafters.song;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Fabian Dietenberger
 */
@RestController
public class SongRestController {

    @RequestMapping("/popular")
    public List<Song> getPopularSongs() {
        final List<Song> songs = new ArrayList<>();
        songs.add(new Song());
        return songs;
    }

    @RequestMapping("/song/{id}")
    public Song getSong(@PathVariable final String id) {
        if (NumberUtils.isNumber(id)) {
            final Long songId = Long.valueOf(id);
            final Song song = new Song();
            song.setId(songId);
            song.setArtist("Mord Fustang");
            song.setTitle("Drivel");
            return song;
        }
        // TODO return 400
        return null;
    }
}
