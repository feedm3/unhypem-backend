package com.codecrafters.song;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import static com.google.common.truth.Truth.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class SongRepositoryTest {

    @Autowired
    private SongRepository songRepository;

    private static final String ARTIST = "Artist";
    private static final String TITLE = "Titls";
    private static final String HYPEM_MEDIA_ID = "mediaId";

    @Test(expected = DataIntegrityViolationException.class)
    public void testArtistMustNoBeNull() {
        final Song song = getSong();
        song.setArtist(null);

        songRepository.saveAndFlush(song);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testTitleMustNoBeNull() {
        final Song song = getSong();
        song.setTitle(null);

        songRepository.saveAndFlush(song);
    }

    @Test
    public void testFindSongByMediaId() {
        songRepository.save(getSong());

        final Song song = songRepository.findOneByHypemMediaId(HYPEM_MEDIA_ID);
        assertThat(song.getHypemMediaId()).isEqualTo(HYPEM_MEDIA_ID);
    }

    private Song getSong() {
        final Song song = new Song();
        song.setArtist(ARTIST);
        song.setTitle(TITLE);
        song.setHypemMediaId(HYPEM_MEDIA_ID);
        return song;
    }
}