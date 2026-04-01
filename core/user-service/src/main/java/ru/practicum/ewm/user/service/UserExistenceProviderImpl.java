package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.contract.UserExistenceProvider;
import ru.practicum.ewm.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserExistenceProviderImpl implements UserExistenceProvider {
    private final UserRepository userRepository;

    @Override
    public boolean existsById(long userId) {
        return userRepository.existsById(userId);
    }
}