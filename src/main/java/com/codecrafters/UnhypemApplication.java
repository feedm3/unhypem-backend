package com.codecrafters;

import com.codecrafters.api.hypem.HypemApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;

@SpringBootApplication
@EnableScheduling
@EnableCaching
public class UnhypemApplication {

    private final Logger LOGGER = LoggerFactory.getLogger(UnhypemApplication.class);

    public static void main(final String[] args) {
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
    public HypemApi hypemApi() {
        return new HypemApi();
    }
}
