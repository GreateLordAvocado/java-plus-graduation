package ru.practicum.ewm.catalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import ru.practicum.ewm.catalog.category.service.EventCategoryUsageClient;
import ru.practicum.ewm.catalog.compilation.service.CompilationEventInternalClient;

@SpringBootApplication(scanBasePackages = {
        "ru.practicum.ewm.catalog",
        "ru.practicum.ewm.common"
})
@EnableFeignClients(basePackageClasses = {
        EventCategoryUsageClient.class,
        CompilationEventInternalClient.class
})
public class CatalogServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }
}