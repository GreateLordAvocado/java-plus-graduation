package ru.practicum.ewm.catalog.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.compilation.contract.CompilationEventProvider;
import ru.practicum.ewm.event.api.dto.EventShortInfo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CompilationEventProviderFeignAdapter implements CompilationEventProvider {

    private final CompilationEventInternalClient compilationEventInternalClient;

    @Override
    public List<EventShortInfo> getShortEventsByIds(List<Long> eventIds) {
        if (eventIds == null || eventIds.isEmpty()) {
            return List.of();
        }
        return compilationEventInternalClient.getShortEventsByIds(eventIds);
    }
}