package com.codecrafters;

import com.codecrafters.api.hypem.HypemApi;
import com.codecrafters.api.hypem.HypemConfiguration;
import com.codecrafters.popular.PopularSongsRepository;
import com.codecrafters.song.SongRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.net.InetAddress;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableJpaAuditing
public class UnhypemApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(UnhypemApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(UnhypemApplication.class, args);
    }

    @Bean
    @Autowired
    CommandLineRunner afterStartup(final Environment environment, final PopularSongsRepository popularSongsRepository, final SongRepository songRepository) {
        return (String... args) -> {
            final String host = InetAddress.getLocalHost().getHostAddress();
            final String port = environment.getProperty("local.server.port");
        };
    }

    @Bean
    @Autowired
    public HypemApi hypemApi(final HypemConfiguration hypemConfiguration) {
        return new HypemApi(hypemConfiguration);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
            }
        };
    }
}
