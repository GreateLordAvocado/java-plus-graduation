package ru.practicum.ewm.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.client.grpc.AnalyzerGrpcClient;
import ru.practicum.stats.client.grpc.CollectorGrpcClient;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.event",
        "ru.practicum.ewm.common",
        "ru.practicum.ewm.catalog.category",
        "ru.practicum.ewm.catalog.compilation"
})
@EntityScan(basePackages = {
        "ru.practicum.ewm.event",
        "ru.practicum.ewm.catalog"
})
@EnableJpaRepositories(basePackages = {
        "ru.practicum.ewm.event",
        "ru.practicum.ewm.catalog"
})
@EnableFeignClients(basePackages = {
        "ru.practicum.ewm.event.client",
        "ru.practicum.ewm.catalog.category.service",
        "ru.practicum.ewm.catalog.compilation.service"
})
@Import({
        StatsClient.class,
        CollectorGrpcClient.class,
        AnalyzerGrpcClient.class
})
public class EventServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}