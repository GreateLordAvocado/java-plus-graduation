package ru.practicum.ewm.analyzer.kafka;

import org.apache.kafka.common.serialization.Deserializer;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.nio.ByteBuffer;
import java.util.Map;

public class EventSimilarityAvroDeserializer implements Deserializer<EventSimilarityAvro> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public EventSimilarityAvro deserialize(String topic, byte[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        try {
            return EventSimilarityAvro.fromByteBuffer(ByteBuffer.wrap(data));
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось десериализовать EventSimilarityAvro", e);
        }
    }

    @Override
    public void close() {

    }
}