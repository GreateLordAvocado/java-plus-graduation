package ru.practicum.ewm.request.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CollectorGrpcClient {

    private final ru.practicum.stats.client.grpc.CollectorGrpcClient delegate;

    public void sendRegisterAction(long userId, long eventId) {
        delegate.sendRegisterAction(userId, eventId);
    }
}