package ru.practicum.ewm.collector.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.collector.config.CollectorKafkaProperties;
import ru.practicum.ewm.stats.avro.UserActionAvro;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserActionProducer {

    private final KafkaTemplate<String, UserActionAvro> kafkaTemplate;
    private final CollectorKafkaProperties collectorKafkaProperties;

    public void send(UserActionAvro action) {
        try {
            String key = String.valueOf(action.getEventId());

            kafkaTemplate.send(collectorKafkaProperties.getUserActions(), key, action)
                    .get(10, TimeUnit.SECONDS);

            log.info(
                    "Отправлено действие пользователя в Kafka: userId={}, eventId={}, actionType={}",
                    action.getUserId(),
                    action.getEventId(),
                    action.getActionType()
            );
        } catch (Exception e) {
            log.error(
                    "Ошибка отправки действия пользователя в Kafka: topic={}, key={}, userId={}, eventId={}, actionType={}",
                    collectorKafkaProperties.getUserActions(),
                    action.getEventId(),
                    action.getUserId(),
                    action.getEventId(),
                    action.getActionType(),
                    e
            );
            throw new IllegalStateException("Не удалось отправить действие пользователя в Kafka", e);
        }
    }
}