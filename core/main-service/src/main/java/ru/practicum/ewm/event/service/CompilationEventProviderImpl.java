package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.contract.CompilationEventProvider;
import ru.practicum.ewm.event.api.dto.EventShortInfo;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationEventProviderImpl implements CompilationEventProvider {
    private final EventRepository eventRepository;

    @Override
    public List<EventShortInfo> getShortEventsByIds(List<Long> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return List.of();
        }

        return eventRepository.findAllById(eventIds).stream()
                .map(EventMapper::toShortDto)
                .map(this::toShortInfo)
                .toList();
    }

    private EventShortInfo toShortInfo(EventShortDto dto) {
        return EventShortInfo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .annotation(dto.getAnnotation())
                .eventDate(dto.getEventDate())
                .paid(dto.isPaid())
                .views(dto.getViews())
                .confirmedRequests(dto.getConfirmedRequests())
                .categoryId(dto.getCategory().getId())
                .categoryName(dto.getCategory().getName())
                .initiatorId(dto.getInitiator().getId())
                .initiatorName(dto.getInitiator().getName())
                .build();
    }
}