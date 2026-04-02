package ru.practicum.ewm.event.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.practicum.ewm.event.api.dto.UserShortInfo;

@FeignClient(name = "user-service", path = "/internal/users")
public interface UserInternalClient {
    @GetMapping("/{userId}/exists")
    boolean existsById(@PathVariable("userId") long userId);

    @GetMapping("/{userId}/short")
    UserShortInfo getShortInfo(@PathVariable("userId") long userId);
}