package ru.practicum.ewm.event.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.event.api.dto.CategoryShortInfo;

@FeignClient(name = "catalog-service", path = "/internal/categories")
public interface CategoryInternalClient {
    @GetMapping("/{categoryId}/exists")
    boolean existsById(@PathVariable("categoryId") long categoryId);

    @GetMapping("/{categoryId}/short")
    CategoryShortInfo getShortInfo(@PathVariable("categoryId") long categoryId);
}