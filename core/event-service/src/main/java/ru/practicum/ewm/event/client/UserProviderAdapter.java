package ru.practicum.ewm.event.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.api.dto.UserShortInfo;
import ru.practicum.ewm.user.contract.UserExistenceProvider;
import ru.practicum.ewm.user.contract.UserShortInfoProvider;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserProviderAdapter implements UserExistenceProvider, UserShortInfoProvider {
    private final UserInternalClient userInternalClient;

    @Override
    public boolean existsById(long userId) {
        try {
            return userInternalClient.existsById(userId);
        } catch (RuntimeException ex) {
            log.warn("Failed to check user existence for userId={}, returning false", userId, ex);
            return false;
        }
    }

    @Override
    public UserShortInfo getShortInfo(long userId) {
        try {
            return userInternalClient.getShortInfo(userId);
        } catch (RuntimeException ex) {
            log.warn("Failed to get user short info for userId={}, returning fallback", userId, ex);
            return fallbackUser(userId);
        }
    }

    @Override
    public Map<Long, UserShortInfo> getShortInfoByIds(Collection<Long> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return Map.of();
        }

        try {
            Map<Long, UserShortInfo> result = userInternalClient.getShortInfoByIds(userIds);
            return result == null ? buildFallbackUsers(userIds) : fillMissingUsers(userIds, result);
        } catch (RuntimeException ex) {
            log.warn("Failed to get users short info batch, returning fallback for ids={}", userIds, ex);
            return buildFallbackUsers(userIds);
        }
    }

    private static Map<Long, UserShortInfo> buildFallbackUsers(Collection<Long> userIds) {
        Map<Long, UserShortInfo> result = new LinkedHashMap<>();
        for (Long userId : userIds) {
            result.put(userId, fallbackUser(userId));
        }
        return result;
    }

    private static Map<Long, UserShortInfo> fillMissingUsers(
            Collection<Long> userIds,
            Map<Long, UserShortInfo> source
    ) {
        Map<Long, UserShortInfo> result = new LinkedHashMap<>();
        for (Long userId : userIds) {
            result.put(userId, source.getOrDefault(userId, fallbackUser(userId)));
        }
        return result;
    }

    private static UserShortInfo fallbackUser(long userId) {
        return UserShortInfo.builder()
                .id(userId)
                .name("unknown user")
                .build();
    }
}