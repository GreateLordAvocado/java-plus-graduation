package ru.practicum.ewm.analyzer.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.analyzer.service.SimilarityUpdateService;
import ru.practicum.ewm.stats.avro.EventSimilarityAvro;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventSimilarityListener {

    private final SimilarityUpdateService similarityUpdateService;

    @KafkaListener(
            topics = "${analyzer.kafka.topic.events-similarity}",
            containerFactory = "eventSimilarityKafkaListenerContainerFactory"
    )
    public void onEventSimilarity(EventSimilarityAvro similarity) {
        if (similarity == null) {
            log.warn("Получено пустое сообщение о сходстве событий");
            return;
        }

        similarityUpdateService.process(similarity);
    }
}