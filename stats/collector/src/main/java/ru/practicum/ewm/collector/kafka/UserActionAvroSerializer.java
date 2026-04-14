package ru.practicum.ewm.collector.kafka;

import org.apache.kafka.common.serialization.Serializer;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.nio.ByteBuffer;
import java.util.Map;

public class UserActionAvroSerializer implements Serializer<UserActionAvro> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, UserActionAvro data) {
        if (data == null) {
            return null;
        }

        try {
            ByteBuffer buffer = data.toByteBuffer();
            byte[] result = new byte[buffer.remaining()];
            buffer.get(result);
            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось сериализовать UserActionAvro", e);
        }
    }

    @Override
    public void close() {

    }
}