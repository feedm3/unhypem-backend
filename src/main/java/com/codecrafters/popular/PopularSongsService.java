package com.codecrafters.popular;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PopularSongsService {

    private final PopularSongsRepository popularSongsRepository;

    public PopularSongsService(final PopularSongsRepository popularSongsRepository) {
        this.popularSongsRepository = popularSongsRepository;
    }

    @Cacheable("popularSongs")
    public PopularSongs getPopularSongs() {
        return popularSongsRepository.findFirstByOrderByCreatedDateDesc();
    }

    @CacheEvict(value = "popularSongs", allEntries = true)
    public void savePopularSongs(final PopularSongs popularSongs) {
        popularSongsRepository.save(popularSongs);
    }

    public long count() {
        return popularSongsRepository.count();
    }

    public void deleteSomeOfTheOldestRecords() {
        final List<PopularSongs> oldest10Charts = popularSongsRepository.findFirst10ByOrderByCreatedDateAsc();
        popularSongsRepository.deleteInBatch(oldest10Charts);
    }
}
