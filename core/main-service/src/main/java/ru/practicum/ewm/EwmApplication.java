package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication
@ComponentScan(
        basePackages = "ru.practicum.ewm",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ANNOTATION,
                        classes = SpringBootApplication.class
                ),
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "ru\\.practicum\\.ewm\\.event\\.client\\..*"
                )
        }
)
public class EwmApplication {
    public static void main(String[] args) {
        SpringApplication.run(EwmApplication.class, args);
    }
}