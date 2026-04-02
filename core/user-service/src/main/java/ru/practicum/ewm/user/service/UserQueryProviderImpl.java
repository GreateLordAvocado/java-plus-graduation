package ru.practicum.ewm.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.common.exception.NotFoundException;
import ru.practicum.ewm.event.api.dto.UserShortInfo;
import ru.practicum.ewm.user.contract.UserExistenceProvider;
import ru.practicum.ewm.user.contract.UserShortInfoProvider;
import ru.practicum.ewm.user.model.User;
import ru.practicum.ewm.user.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class UserQueryProviderImpl implements UserExistenceProvider, UserShortInfoProvider {
    private final UserRepository userRepository;

    @Override
    public boolean existsById(long userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public UserShortInfo getShortInfo(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id=" + userId + " was not found"));

        return UserShortInfo.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}