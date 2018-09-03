package com.codecrafters;

import com.codecrafters.api.hypem.HypemApi;
import com.codecrafters.api.hypem.HypemConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@EnableScheduling
@EnableCaching
@EnableJpaAuditing
public class UnhypemApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UnhypemApplication.class, args);
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
    public CorsFilter corsFilter() {
        final CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
        config.addAllowedMethod("*");

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
