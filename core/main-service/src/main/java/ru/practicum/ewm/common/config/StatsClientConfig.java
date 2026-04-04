package ru.practicum.ewm.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.stats.client.StatsClient;

@Configuration
public class StatsClientConfig {

    @Bean
    public StatsClient statsClient(
            @Value("${stats-server.url:http://stats-server}") String statsServerUrl,
            RestTemplateBuilder restTemplateBuilder,
            ObjectMapper objectMapper,
            DiscoveryClient discoveryClient
    ) {
        return new StatsClient(
                statsServerUrl,
                restTemplateBuilder,
                objectMapper,
                discoveryClient
        );
    }
}