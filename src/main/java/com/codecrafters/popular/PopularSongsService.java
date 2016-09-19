package com.codecrafters.popular;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class PopularSongsService {

    private final PopularSongsRepository repository;

    public PopularSongsService(final PopularSongsRepository repository) {
        this.repository = repository;
    }

    @Cacheable("popularSongs")
    public PopularSongs getPopularSongs() {
        return repository.findFirstByOrderByTimestampAsc();
    }

    @CacheEvict(value = "popularSongs", allEntries = true)
    public void savePopularSongs(final PopularSongs popularSongs) {
        repository.save(popularSongs);
    }
}
