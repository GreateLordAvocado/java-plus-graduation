package ru.practicum.ewm.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.contract.ConfirmedRequestCounter;
import ru.practicum.ewm.request.model.RequestStatus;
import ru.practicum.ewm.request.repository.RequestRepository;

@Component
@RequiredArgsConstructor
public class RequestConfirmedRequestCounter implements ConfirmedRequestCounter {
    private final RequestRepository requestRepository;

    @Override
    public long countConfirmedRequests(long eventId) {
        return requestRepository.countByEventIdAndStatus(eventId, RequestStatus.CONFIRMED);
    }
}