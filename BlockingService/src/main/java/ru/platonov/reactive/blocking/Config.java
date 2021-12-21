package ru.platonov.reactive.blocking;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Config
 * <p>
 * </p>
 *
 * @author Platonov Alexey
 * @since 19.12.2021
 */
@Slf4j
@Configuration
public class Config {

    @Value("${http.client.connection.max}")
    private Integer maxConnTotal;

    @Value("${http.client.connection.max.per.route}")
    private Integer maxConnectionPerRoute;

    @Value("${http.client.default}")
    private boolean isDefault;

    @Bean
    public RestTemplate restTemplate() {
        if (isDefault) {
            log.info("Used default http client config");
            return new RestTemplate();
        }

        HttpClient httpClient = HttpClientBuilder.create()
                                                 .setMaxConnTotal(maxConnTotal) //default 20
                                                 .setMaxConnPerRoute(maxConnectionPerRoute) //2
                                                 .build();

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

        log.info("Used http client with: maxConnTotal={}, maxConnectionPerRoute={}", maxConnTotal, maxConnectionPerRoute);
        return restTemplate;
    }

}
