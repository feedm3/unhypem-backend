package com.codecrafters.charts;

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
public class ChartsServiceIntegrationTest {

    @Autowired
    private ChartsService service;

    @Autowired
    private CacheManager cacheManager;

    @MockBean
    private ChartsRepository repository;

    @Before
    public void beforeTest() {
        evictCache();
    }

    @Test
    public void repositoryResponseWillBeCached() {
        service.getCharts();
        service.getCharts();

        verify(repository, Mockito.times(1)).findFirstByOrderByCreatedDateDesc();
    }

    @Test
    public void chacheWillBeRenewedAfterUpdate() {
        service.getCharts();
        service.getCharts();
        service.saveCharts(new Charts());
        service.getCharts();
        service.getCharts();

        verify(repository, Mockito.times(2)).findFirstByOrderByCreatedDateDesc();
    }

    private void evictCache() {
        cacheManager.getCacheNames().forEach(name -> cacheManager.getCache(name).clear());
    }
}
