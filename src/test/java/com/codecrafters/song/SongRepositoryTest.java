package com.codecrafters.song;

import com.codecrafters.testutils.TestSongBuilder;
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

    @Test(expected = DataIntegrityViolationException.class)
    public void testArtistMustNoBeNull() {
        final Song song = TestSongBuilder.getSong();
        song.setArtist(null);

        songRepository.saveAndFlush(song);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testTitleMustNoBeNull() {
        final Song song = TestSongBuilder.getSong();
        song.setTitle(null);

        songRepository.saveAndFlush(song);
    }

    @Test
    public void testFindSongByMediaId() {
        songRepository.save(TestSongBuilder.getSong());

        final Song song = songRepository.findOneByHypemMediaId(TestSongBuilder.HYPEM_MEDIA_ID);
        assertThat(song.getHypemMediaId()).isEqualTo(TestSongBuilder.HYPEM_MEDIA_ID);
    }
}