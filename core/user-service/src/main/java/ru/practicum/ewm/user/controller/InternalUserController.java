package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.event.api.dto.UserShortInfo;
import ru.practicum.ewm.user.service.UserQueryProviderImpl;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserQueryProviderImpl userQueryProvider;

    @GetMapping("/{userId}/exists")
    public boolean existsById(@PathVariable long userId) {
        return userQueryProvider.existsById(userId);
    }

    @GetMapping("/{userId}/short")
    public UserShortInfo getShortInfo(@PathVariable long userId) {
        return userQueryProvider.getShortInfo(userId);
    }
}