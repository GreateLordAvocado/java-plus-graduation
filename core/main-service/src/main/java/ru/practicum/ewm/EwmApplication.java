package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = {
                "ru.practicum.ewm",
                "ru.practicum.stats"
        },
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.user\\.controller\\..*"
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.catalog\\.category\\.controller\\..*"
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.catalog\\.compilation\\.controller\\..*"
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.user\\.UserServiceApplication"
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.catalog\\.CatalogServiceApplication"
                )
        }
)
public class EwmApplication {
    public static void main(String[] args) {
        SpringApplication.run(EwmApplication.class, args);
    }
}