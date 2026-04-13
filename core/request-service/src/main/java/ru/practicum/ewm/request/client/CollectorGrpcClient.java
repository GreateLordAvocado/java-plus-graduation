package ru.practicum.ewm.request.client;

import com.google.protobuf.Timestamp;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.proto.collector.ActionTypeProto;
import ru.practicum.ewm.stats.proto.collector.UserActionControllerGrpc;
import ru.practicum.ewm.stats.proto.collector.UserActionProto;

import java.time.Instant;

@Slf4j
@Component
public class CollectorGrpcClient {

    @GrpcClient("collector")
    private UserActionControllerGrpc.UserActionControllerBlockingStub collectorStub;

    public void sendRegisterAction(long userId, long eventId) {
        try {
            Instant now = Instant.now();
            Timestamp timestamp = Timestamp.newBuilder()
                    .setSeconds(now.getEpochSecond())
                    .setNanos(now.getNano())
                    .build();

            UserActionProto request = UserActionProto.newBuilder()
                    .setUserId(userId)
                    .setEventId(eventId)
                    .setActionType(ActionTypeProto.ACTION_REGISTER)
                    .setTimestamp(timestamp)
                    .build();

            collectorStub.collectUserAction(request);

            log.info("Отправлено действие REGISTER в collector: userId={}, eventId={}", userId, eventId);
        } catch (Exception e) {
            log.warn(
                    "Не удалось отправить действие REGISTER в collector для userId={}, eventId={}: {}",
                    userId,
                    eventId,
                    e.getMessage()
            );
        }
    }
}