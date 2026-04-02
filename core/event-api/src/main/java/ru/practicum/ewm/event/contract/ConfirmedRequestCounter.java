package ru.practicum.ewm.event.contract;

public interface ConfirmedRequestCounter {
    long countConfirmedRequests(long eventId);
}