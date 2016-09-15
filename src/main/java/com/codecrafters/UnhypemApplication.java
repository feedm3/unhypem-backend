package com.codecrafters;

import com.codecrafters.api.hypem.HypemPlaylistAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;

@SpringBootApplication
@EnableScheduling
public class UnhypemApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(UnhypemApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(UnhypemApplication.class, args);
	}

	@Bean
	CommandLineRunner afterStartup(final Environment environment) {
		return args -> {
            final String host = InetAddress.getLocalHost().getHostAddress();
            final String port = environment.getProperty("local.server.port");

            LOGGER.info("Server started at {}:{}", host, port);
        };
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

    @Bean
    public HypemPlaylistAdapter hypemPlaylistAdapter() {
        return new HypemPlaylistAdapter(restTemplate());
    }
}
