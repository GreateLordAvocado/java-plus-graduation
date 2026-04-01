package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.category.contract.CategoryUsageChecker;
import ru.practicum.ewm.event.repository.EventRepository;

@Component
@RequiredArgsConstructor
public class EventCategoryUsageChecker implements CategoryUsageChecker {
    private final EventRepository eventRepository;

    @Override
    public boolean isCategoryUsed(long categoryId) {
        return eventRepository.existsByCategoryId(categoryId);
    }
}