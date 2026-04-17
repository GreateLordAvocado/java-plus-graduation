package ru.practicum.stats.client.grpc;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.stats.proto.dashboard.InteractionsCountRequestProto;
import ru.practicum.ewm.stats.proto.dashboard.RecommendationsControllerGrpc;
import ru.practicum.ewm.stats.proto.dashboard.RecommendedEventProto;
import ru.practicum.ewm.stats.proto.dashboard.SimilarEventsRequestProto;
import ru.practicum.ewm.stats.proto.dashboard.UserPredictionsRequestProto;
import ru.practicum.stats.client.dto.RecommendedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AnalyzerGrpcClient {

    @GrpcClient("analyzer")
    private RecommendationsControllerGrpc.RecommendationsControllerBlockingStub analyzerStub;

    public List<RecommendedEvent> getRecommendationsForUser(long userId, int maxResults) {
        UserPredictionsRequestProto request = UserPredictionsRequestProto.newBuilder()
                .setUserId(userId)
                .setMaxResults(maxResults)
                .build();

        List<RecommendedEvent> result = new ArrayList<>();
        analyzerStub.getRecommendationsForUser(request)
                .forEachRemaining(item -> result.add(toDto(item)));

        return result;
    }

    public List<RecommendedEvent> getSimilarEvents(long eventId, long userId, int maxResults) {
        SimilarEventsRequestProto request = SimilarEventsRequestProto.newBuilder()
                .setEventId(eventId)
                .setUserId(userId)
                .setMaxResults(maxResults)
                .build();

        List<RecommendedEvent> result = new ArrayList<>();
        analyzerStub.getSimilarEvents(request)
                .forEachRemaining(item -> result.add(toDto(item)));

        return result;
    }

    public Map<Long, Double> getInteractionsCount(Collection<Long> eventIds) {
        InteractionsCountRequestProto.Builder builder = InteractionsCountRequestProto.newBuilder();
        builder.addAllEventId(eventIds);

        Map<Long, Double> result = new LinkedHashMap<>();
        analyzerStub.getInteractionsCount(builder.build())
                .forEachRemaining(item -> result.put(item.getEventId(), item.getScore()));

        return result;
    }

    private static RecommendedEvent toDto(RecommendedEventProto proto) {
        return new RecommendedEvent(proto.getEventId(), proto.getScore());
    }
}