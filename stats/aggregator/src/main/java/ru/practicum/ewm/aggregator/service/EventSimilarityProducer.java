package ru.practicum.ewm.aggregator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.aggregator.config.AggregatorKafkaProperties;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventSimilarityProducer {

    private final KafkaTemplate<String, EventSimilarityAvro> kafkaTemplate;
    private final AggregatorKafkaProperties kafkaProperties;

    public void send(EventSimilarityAvro similarity) {
        try {
            String key = similarity.getEventA() + "-" + similarity.getEventB();

            kafkaTemplate.send(kafkaProperties.getEventsSimilarity(), key, similarity)
                    .get(10, TimeUnit.SECONDS);

            log.info(
                    "Отправлено сходство событий в Kafka: eventA={}, eventB={}, score={}",
                    similarity.getEventA(),
                    similarity.getEventB(),
                    similarity.getScore()
            );
        } catch (Exception e) {
            throw new IllegalStateException("Не удалось отправить сходство событий в Kafka", e);
        }
    }
}