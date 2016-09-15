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

    private final SongRepository songRepository;

    @Autowired
    public SongRestController(final SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @RequestMapping("/song/{hypemMediaId}")
    public ResponseEntity<Song> getSong(@PathVariable final String hypemMediaId) {
        final Song song = songRepository.findOneByHypemMediaId(hypemMediaId);
        if (song == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(song);
    }
}
