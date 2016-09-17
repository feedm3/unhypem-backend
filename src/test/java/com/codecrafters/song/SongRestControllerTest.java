package com.codecrafters.song;

import com.codecrafters.testutils.TestSongBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SongRestController.class)
public class SongRestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SongRepository songRepository;

    @Test
    public void getSongByMediaId() throws Exception {
        given(songRepository.findOneByHypemMediaId(TestSongBuilder.HYPEM_MEDIA_ID))
                .willReturn(TestSongBuilder.getSong());

        mvc.perform(get("/song/" + TestSongBuilder.HYPEM_MEDIA_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hypemMediaId").value(TestSongBuilder.HYPEM_MEDIA_ID));
    }

    @Test
    public void get404WhenMediaCannotBeFound() throws Exception {
        given(songRepository.findOneByHypemMediaId(any())).willReturn(null);

        mvc.perform(get("/song/" + TestSongBuilder.HYPEM_MEDIA_ID))
                .andExpect(status().isNotFound());
    }
}