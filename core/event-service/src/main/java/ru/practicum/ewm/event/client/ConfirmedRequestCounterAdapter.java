package ru.practicum.ewm.event.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.contract.ConfirmedRequestCounter;

@Component
@RequiredArgsConstructor
public class ConfirmedRequestCounterAdapter implements ConfirmedRequestCounter {

    private final RequestInternalClient requestInternalClient;

    @Override
    public long countConfirmedRequests(long eventId) {
        return requestInternalClient.countConfirmedRequests(eventId);
    }
}