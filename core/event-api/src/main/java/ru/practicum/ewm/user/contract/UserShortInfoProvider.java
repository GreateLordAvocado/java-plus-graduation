package ru.practicum.ewm.user.contract;

import ru.practicum.ewm.event.api.dto.UserShortInfo;

public interface UserShortInfoProvider {
    UserShortInfo getShortInfo(long userId);
}