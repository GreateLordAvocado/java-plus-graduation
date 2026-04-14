package ru.practicum.ewm.aggregator.kafka;

import org.apache.kafka.common.serialization.Serializer;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.nio.ByteBuffer;
import java.util.Map;

public class EventSimilarityAvroSerializer implements Serializer<EventSimilarityAvro> {

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, EventSimilarityAvro data) {
        if (data == null) {
            return null;
        }

        try {
            ByteBuffer buffer = data.toByteBuffer();
            byte[] result = new byte[buffer.remaining()];
            buffer.get(result);
            return result;
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось сериализовать EventSimilarityAvro", e);
        }
    }

    @Override
    public void close() {

    }
}