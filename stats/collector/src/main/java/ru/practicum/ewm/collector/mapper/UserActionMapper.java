package ru.practicum.ewm.collector.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;
import ru.practicum.ewm.stats.avro.UserActionAvro;
import ru.practicum.ewm.stats.proto.collector.ActionTypeProto;
import ru.practicum.ewm.stats.proto.collector.UserActionProto;

import java.time.Instant;

@Component
public class UserActionMapper {

    public UserActionAvro toAvro(UserActionProto proto) {
        validate(proto);

        return UserActionAvro.newBuilder()
                .setUserId(proto.getUserId())
                .setEventId(proto.getEventId())
                .setActionType(mapActionType(proto.getActionType()))
                .setTimestamp(Instant.ofEpochMilli(0L))
                .build();
    }

    private static ActionTypeAvro mapActionType(ActionTypeProto actionType) {
        return switch (actionType) {
            case ACTION_VIEW -> ActionTypeAvro.VIEW;
            case ACTION_REGISTER -> ActionTypeAvro.REGISTER;
            case ACTION_LIKE -> ActionTypeAvro.LIKE;
            case UNRECOGNIZED -> throw new IllegalArgumentException("Неизвестный тип действия");
        };
    }

    private static void validate(UserActionProto proto) {
        if (proto.getUserId() <= 0) {
            throw new IllegalArgumentException("user_id должен быть положительным");
        }
        if (proto.getEventId() <= 0) {
            throw new IllegalArgumentException("event_id должен быть положительным");
        }
        if (!proto.hasTimestamp()) {
            throw new IllegalArgumentException("timestamp обязателен");
        }
    }
}