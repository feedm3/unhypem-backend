package com.codecrafters.popular;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.verify;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PopularSongsServiceIntegrationTest {

    @Autowired
    private PopularSongsService service;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private PopularSongsRepository repository;

    @Before
    public void beforeTest() {
        evictCache();
    }

    @Test
    public void repositoryResponseWillBeCached() {
        service.getPopularSongs();
        service.getPopularSongs();

        verify(repository, Mockito.times(1)).findFirstByOrderByTimestampAsc();
    }

    @Test
    public void chacheWillBeRenewedAfterUpdate() {
        service.getPopularSongs();
        service.getPopularSongs();
        service.savePopularSongs(new PopularSongs());
        service.getPopularSongs();
        service.getPopularSongs();

        verify(repository, Mockito.times(2)).findFirstByOrderByTimestampAsc();
    }

    private void evictCache() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }
}
