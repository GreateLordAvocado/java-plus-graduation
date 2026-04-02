package ru.practicum.ewm.request.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.request.service.RequestConfirmedRequestCounter;

@RestController
@RequestMapping("/internal/requests")
@RequiredArgsConstructor
public class RequestInternalController {

    private final RequestConfirmedRequestCounter confirmedRequestCounter;

    @GetMapping("/events/{eventId}/confirmed-count")
    public long countConfirmedRequests(@PathVariable long eventId) {
        return confirmedRequestCounter.countConfirmedRequests(eventId);
    }
}