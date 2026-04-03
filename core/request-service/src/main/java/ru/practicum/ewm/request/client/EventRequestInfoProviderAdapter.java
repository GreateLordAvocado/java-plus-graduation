package ru.practicum.ewm.request.client;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.common.exception.NotFoundException;
import ru.practicum.ewm.request.contract.EventRequestInfo;
import ru.practicum.ewm.request.contract.EventRequestInfoProvider;

@Component
@RequiredArgsConstructor
public class EventRequestInfoProviderAdapter implements EventRequestInfoProvider {

    private final EventInternalClient eventInternalClient;

    @Override
    public EventRequestInfo getEventRequestInfo(long eventId) {
        try {
            return eventInternalClient.getEventRequestInfo(eventId);
        } catch (FeignException.NotFound e) {
            throw new NotFoundException("Event with id=" + eventId + " was not found");
        }
    }
}