package com.codecrafters.charts;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ChartsService {

    private final ChartsRepository chartsRepository;

    public ChartsService(final ChartsRepository chartsRepository) {
        this.chartsRepository = chartsRepository;
    }

    @Cacheable("charts")
    public Charts getCharts() {
        return chartsRepository.findFirstByOrderByCreatedDateDesc();
    }

    @CacheEvict(value = "charts", allEntries = true)
    public void saveCharts(final Charts charts) {
        chartsRepository.save(charts);
    }

    public long count() {
        return chartsRepository.count();
    }

    public void deleteSomeOfTheOldestRecords() {
        final List<Charts> oldest10Charts = chartsRepository.findFirst10ByOrderByCreatedDateAsc();
        chartsRepository.deleteInBatch(oldest10Charts);
    }
}
