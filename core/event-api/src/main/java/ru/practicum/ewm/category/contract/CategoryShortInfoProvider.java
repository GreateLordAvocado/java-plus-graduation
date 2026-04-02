package ru.practicum.ewm.category.contract;

import ru.practicum.ewm.event.api.dto.CategoryShortInfo;

public interface CategoryShortInfoProvider {
    CategoryShortInfo getShortInfo(long categoryId);
}