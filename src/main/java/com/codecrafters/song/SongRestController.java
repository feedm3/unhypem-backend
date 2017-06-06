package com.codecrafters.song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fabian Dietenberger
 */
@RestController
class SongRestController {

    private final SongService songService;

    @Autowired
    public SongRestController(final SongService songService) {
        this.songService = songService;
    }

    @RequestMapping("/song/{hypemMediaId}")
    public ResponseEntity<Song> getSong(@PathVariable final String hypemMediaId) {
        final Song song = songService.findOneByHypemMediaId(hypemMediaId);
        if (song == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(song);
    }
}
