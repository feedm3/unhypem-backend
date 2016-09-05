package com.codecrafters;

import com.codecrafters.popular.PopularSongs;
import com.codecrafters.popular.PopularSongsRepository;
import com.codecrafters.song.Song;
import com.codecrafters.song.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@SpringBootApplication
public class UnhypemApplication {

	@Autowired
	private SongRepository songRepository;

	@Autowired
	private PopularSongsRepository popularSongsRepository;

	public static void main(String[] args) {
		SpringApplication.run(UnhypemApplication.class, args);
	}

	@Bean
	CommandLineRunner afterStartup() {
		return args -> {
			// add some test data
			final Map<String, String> titleArtist = new HashMap<>();
			titleArtist.put("Mord Fustang", "Drivel");
			titleArtist.put("Deadmau5", "Soma");
			titleArtist.put("Goodluck", "Back in the day");

			titleArtist.forEach((artist, title) -> {
				final Song song = new Song();
				song.setArtist(artist);
				song.setTitle(title);
				songRepository.save(song);
			});

			final SortedMap<Integer, Song> currentCharts = new TreeMap<>();
			int position = 1;
			for (final Song song : songRepository.findAll()) {
				currentCharts.put(position, song);
				position++;
			}

			final PopularSongs popularSongs = new PopularSongs();
			popularSongs.setSongs(currentCharts);
			popularSongs.setTimestamp(LocalDateTime.now());
			popularSongsRepository.save(popularSongs);
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
