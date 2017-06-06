package com.codecrafters.song;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(final SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song findOneByHypemMediaId(final String hypemMediaId) {
        return songRepository.findOneByHypemMediaId(hypemMediaId);
    }

    public Song save(final Song song) {
        if (StringUtils.isNotBlank(song.getHypemMediaId())) {
            final Song songFromHypem = songRepository.findOneByHypemMediaId(song.getHypemMediaId());
            if (songFromHypem != null) {
                song.setId(songFromHypem.getId());
                songRepository.save(song);
            }
        }
        return songRepository.save(song);
    }

    public long count() {
        return songRepository.count();
    }

    public void deleteSomeOfTheOldestRecords() {
        final List<Song> oldest100Songs = songRepository.findFirst100ByOrderByCreatedDateAsc();
        songRepository.deleteInBatch(oldest100Songs);
    }
}
