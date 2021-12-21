package ru.platonov.reactive;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import java.beans.BeanProperty;

/**
 * Config
 * <p>
 * </p>
 *
 * @author Platonov Alexey
 * @since 16.12.2021
 */
@Configuration
//@EnableWebFlux
public class Config {

    @Value("${http.client.url}")
    private String url;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                        .baseUrl(url)
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .build();
    }


}
