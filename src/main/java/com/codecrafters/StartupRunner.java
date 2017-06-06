package com.codecrafters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;


@Component
public class StartupRunner implements CommandLineRunner {

    private final Logger LOGGER = LoggerFactory.getLogger(StartupRunner.class);

    private final Environment environment;

    public StartupRunner(final Environment environment) {
        this.environment = environment;
    }

    @Override
    public void run(final String... args) throws Exception {
        final String host = InetAddress.getLocalHost().getHostAddress();
        final String port = environment.getProperty("local.server.port");
        final String databaseUrl = environment.getProperty("database.url");

        LOGGER.info("Server started at {}:{}", host, port);
        LOGGER.info("Database url: {}", databaseUrl);
    }
}
