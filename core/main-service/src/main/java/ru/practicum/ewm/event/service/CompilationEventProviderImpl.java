package ru.practicum.ewm.event.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.contract.CompilationEventProvider;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.event.mapper.EventMapper;
import ru.practicum.ewm.event.repository.EventRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationEventProviderImpl implements CompilationEventProvider {
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getShortEventsByIds(List<Long> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return List.of();
        }

        return eventRepository.findAllById(eventIds).stream()
                .map(EventMapper::toShortDto)
                .toList();
    }
}