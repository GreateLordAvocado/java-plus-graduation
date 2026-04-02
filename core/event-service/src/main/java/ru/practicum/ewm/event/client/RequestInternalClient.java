package ru.practicum.ewm.event.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "request-service", path = "/internal/requests")
public interface RequestInternalClient {

    @GetMapping("/events/{eventId}/confirmed-count")
    long countConfirmedRequests(@PathVariable("eventId") long eventId);
}