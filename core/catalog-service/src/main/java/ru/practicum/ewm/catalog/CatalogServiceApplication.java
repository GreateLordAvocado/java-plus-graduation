package ru.practicum.ewm.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.catalog",
        "ru.practicum.ewm.common"
})
@EnableFeignClients(basePackages = {
        "ru.practicum.ewm.catalog.category.service",
        "ru.practicum.ewm.catalog.compilation.service"
})
public class CatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}