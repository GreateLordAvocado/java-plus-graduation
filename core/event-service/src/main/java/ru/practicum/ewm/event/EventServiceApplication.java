package ru.practicum.ewm.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.event",
        "ru.practicum.ewm.common",
        "ru.practicum.stats"
})
@EnableFeignClients(basePackages = "ru.practicum.ewm.event.client")
public class EventServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(EventServiceApplication.class, args);
    }
}