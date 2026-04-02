package ru.practicum.ewm.event.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.api.dto.UserShortInfo;
import ru.practicum.ewm.user.contract.UserExistenceProvider;
import ru.practicum.ewm.user.contract.UserShortInfoProvider;

@Component
@RequiredArgsConstructor
public class UserProviderAdapter implements UserExistenceProvider, UserShortInfoProvider {
    private final UserInternalClient userInternalClient;

    @Override
    public boolean existsById(long userId) {
        return userInternalClient.existsById(userId);
    }

    @Override
    public UserShortInfo getShortInfo(long userId) {
        return userInternalClient.getShortInfo(userId);
    }
}