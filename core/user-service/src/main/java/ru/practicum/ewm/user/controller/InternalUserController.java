package ru.practicum.ewm.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.event.api.dto.UserShortInfo;
import ru.practicum.ewm.user.contract.UserExistenceProvider;
import ru.practicum.ewm.user.contract.UserShortInfoProvider;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {
    private final UserExistenceProvider userExistenceProvider;
    private final UserShortInfoProvider userShortInfoProvider;

    @GetMapping("/{userId}/exists")
    public boolean existsById(@PathVariable long userId) {
        return userExistenceProvider.existsById(userId);
    }

    @GetMapping("/{userId}/short")
    public UserShortInfo getShortInfo(@PathVariable long userId) {
        return userShortInfoProvider.getShortInfo(userId);
    }
}