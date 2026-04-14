package ru.practicum.ewm.analyzer.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.nio.ByteBuffer;
import java.util.Map;

public class UserActionAvroDeserializer implements Deserializer<UserActionAvro> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public UserActionAvro deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        try {
            return UserActionAvro.fromByteBuffer(ByteBuffer.wrap(data));
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось десериализовать UserActionAvro", e);
        }
    }

    @Override
    public void close() {

    }
}