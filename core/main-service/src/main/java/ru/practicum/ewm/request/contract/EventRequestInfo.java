package ru.practicum.ewm.request.contract;

import lombok.Builder;
import lombok.Value;
import ru.practicum.ewm.event.model.EventState;

@Value
@Builder
public class EventRequestInfo {
    long eventId;
    long initiatorId;
    EventState state;
    long participantLimit;
    boolean requestModeration;
}