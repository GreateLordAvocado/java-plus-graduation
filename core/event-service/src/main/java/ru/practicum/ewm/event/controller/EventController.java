package ru.practicum.ewm.event.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.common.exception.BadRequestException;
import ru.practicum.ewm.event.api.dto.EventFullDto;
import ru.practicum.ewm.event.api.dto.EventSortOption;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.service.PublicEventService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static ru.practicum.stats.common.Constants.DATE_TIME_FORMAT;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {
    private final PublicEventService eventService;

    @GetMapping("/recommendations")
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getRecommendations(
            @RequestHeader("X-EWM-USER-ID") long userId,
            @Positive @RequestParam(defaultValue = "10") int size
    ) {
        return eventService.getRecommendations(userId, size);
    }

    @PutMapping("/{eventId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void likeEvent(
            @PathVariable long eventId,
            @RequestHeader("X-EWM-USER-ID") long userId
    ) {
        LocalDateTime eventDate = eventService.getPublishedEventDate(eventId);
        if (eventDate == null || eventDate.isAfter(LocalDateTime.now())) {
            throw new BadRequestException("The user can like only events that have already taken place");
        }

        eventService.likeEvent(eventId, userId);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto findPublishedEvent(
            @PathVariable long id,
            @RequestHeader("X-EWM-USER-ID") long userId,
            HttpServletRequest request
    ) {
        return eventService.findPublishedEvent(id, userId, request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> findEventsByCriteria(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) Set<Long> categories,
            @RequestParam(required = false) Boolean paid,
            @DateTimeFormat(pattern = DATE_TIME_FORMAT)
            @RequestParam(required = false) LocalDateTime rangeStart,
            @DateTimeFormat(pattern = DATE_TIME_FORMAT)
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") boolean onlyAvailable,
            @RequestParam(defaultValue = "VIEWS") EventSortOption sort,
            @PositiveOrZero @RequestParam(defaultValue = "0") int from,
            @Positive @RequestParam(defaultValue = "10") int size,
            HttpServletRequest request
    ) {
        return eventService.findEventsByCriteria(
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size,
                request
        );
    }
}