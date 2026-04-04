package ru.practicum.ewm.catalog.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.contract.CategoryUsageChecker;

@Component
@RequiredArgsConstructor
@Slf4j
public class CategoryUsageCheckerFeignAdapter implements CategoryUsageChecker {

    private final EventCategoryUsageClient eventCategoryUsageClient;

    @Override
    public boolean isCategoryUsed(long categoryId) {
        try {
            return eventCategoryUsageClient.isCategoryUsed(categoryId);
        } catch (RuntimeException ex) {
            log.warn("Failed to check category usage for categoryId={}, returning true as safe fallback",
                    categoryId, ex);
            return true;
        }
    }
}