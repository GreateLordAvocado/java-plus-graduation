package ru.practicum.ewm.request.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.request.contract.EventRequestInfo;
import ru.practicum.ewm.request.contract.EventRequestInfoProvider;

@Component
@RequiredArgsConstructor
public class EventRequestInfoProviderAdapter implements EventRequestInfoProvider {

    private final EventInternalClient eventInternalClient;

    @Override
    public EventRequestInfo getEventRequestInfo(long eventId) {
        return eventInternalClient.getEventRequestInfo(eventId);
    }
}