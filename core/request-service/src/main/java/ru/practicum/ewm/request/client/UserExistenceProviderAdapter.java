package ru.practicum.ewm.request.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.contract.UserExistenceProvider;

@Component
@RequiredArgsConstructor
public class UserExistenceProviderAdapter implements UserExistenceProvider {

    private final UserInternalClient userInternalClient;

    @Override
    public boolean existsById(long userId) {
        return userInternalClient.existsById(userId);
    }
}