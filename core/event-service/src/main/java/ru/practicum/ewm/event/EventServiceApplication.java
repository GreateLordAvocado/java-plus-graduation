package ru.practicum.ewm.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.stats.client.StatsClient;
import ru.practicum.stats.client.grpc.AnalyzerGrpcClient;
import ru.practicum.stats.client.grpc.CollectorGrpcClient;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.event",
        "ru.practicum.ewm.common"
})
@EnableFeignClients(basePackages = {
        "ru.practicum.ewm.event.client"
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