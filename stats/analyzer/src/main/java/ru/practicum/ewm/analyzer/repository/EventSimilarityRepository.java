package ru.practicum.ewm.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewm.analyzer.model.EventSimilarityEntity;

import java.util.Optional;
import java.util.List;

public interface EventSimilarityRepository extends JpaRepository<EventSimilarityEntity, Long> {

    Optional<EventSimilarityEntity> findByEventAAndEventB(Long eventA, Long eventB);

    List<EventSimilarityEntity> findAllByEventAOrEventB(Long eventA, Long eventB);
}