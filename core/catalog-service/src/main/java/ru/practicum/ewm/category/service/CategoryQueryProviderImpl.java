package ru.practicum.ewm.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.contract.CategoryExistenceProvider;
import ru.practicum.ewm.category.contract.CategoryShortInfoProvider;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.category.repository.CategoryRepository;
import ru.practicum.ewm.common.exception.NotFoundException;
import ru.practicum.ewm.event.api.dto.CategoryShortInfo;

@Component
@RequiredArgsConstructor
public class CategoryQueryProviderImpl implements CategoryExistenceProvider, CategoryShortInfoProvider {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean existsById(long categoryId) {
        return categoryRepository.existsById(categoryId);
    }

    @Override
    public CategoryShortInfo getShortInfo(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Category with id=" + categoryId + " was not found"));

        return CategoryShortInfo.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}