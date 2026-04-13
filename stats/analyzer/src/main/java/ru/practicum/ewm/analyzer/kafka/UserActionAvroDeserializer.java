package ru.practicum.ewm.analyzer.kafka;

import org.apache.avro.io.BinaryDecoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.kafka.common.serialization.Deserializer;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.io.IOException;
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
            SpecificDatumReader<UserActionAvro> reader = new SpecificDatumReader<>(UserActionAvro.class);
            BinaryDecoder decoder = DecoderFactory.get().binaryDecoder(data, null);
            return reader.read(null, decoder);
        } catch (IOException e) {
            throw new IllegalStateException("Не удалось десериализовать UserActionAvro", e);
        }
    }

    @Override
    public void close() {

    }
}