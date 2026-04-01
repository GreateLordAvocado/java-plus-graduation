package ru.practicum.ewm.compilation.contract;

import ru.practicum.ewm.event.dto.EventShortDto;

import java.util.List;

public interface CompilationEventProvider {
    List<EventShortDto> getShortEventsByIds(List<Long> eventIds);
}