package ru.practicum.ewm.analyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.analyzer.config.RecommendationWeightsProperties;
import ru.practicum.ewm.stats.avro.ActionTypeAvro;

@Component
@RequiredArgsConstructor
public class ActionWeightResolver {

    private final RecommendationWeightsProperties weightsProperties;

    public double resolve(ActionTypeAvro actionType) {
        return switch (actionType) {
            case VIEW -> weightsProperties.getView();
            case REGISTER -> weightsProperties.getRegister();
            case LIKE -> weightsProperties.getLike();
        };
    }
}