package ru.practicum.ewm.request;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import ru.practicum.stats.client.grpc.CollectorGrpcClient;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.request",
        "ru.practicum.ewm.common"
})
@EnableFeignClients(basePackages = "ru.practicum.ewm.request.client")
@Import(CollectorGrpcClient.class)
public class RequestServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RequestServiceApplication.class, args);
    }
}