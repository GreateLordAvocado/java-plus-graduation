package ru.practicum.ewm.event.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.contract.CategoryExistenceProvider;
import ru.practicum.ewm.category.contract.CategoryShortInfoProvider;
import ru.practicum.ewm.event.api.dto.CategoryShortInfo;

@Component
@RequiredArgsConstructor
public class CategoryProviderAdapter implements CategoryExistenceProvider, CategoryShortInfoProvider {
    private final CategoryInternalClient categoryInternalClient;

    @Override
    public boolean existsById(long categoryId) {
        return categoryInternalClient.existsById(categoryId);
    }

    @Override
    public CategoryShortInfo getShortInfo(long categoryId) {
        return categoryInternalClient.getShortInfo(categoryId);
    }
}