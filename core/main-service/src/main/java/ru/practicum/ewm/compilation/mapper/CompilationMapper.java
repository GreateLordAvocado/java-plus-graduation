package ru.practicum.ewm.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.category.dto.CategoryDto;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.api.dto.EventShortInfo;
import ru.practicum.ewm.event.dto.EventShortDto;
import ru.practicum.ewm.user.dto.UserShortDto;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CompilationMapper {
    public static Compilation from(NewCompilationDto newCompilation) {
        return Compilation.builder()
                .title(newCompilation.getTitle())
                .pinned(newCompilation.getPinned() != null ? newCompilation.getPinned() : false)
                .eventIds(newCompilation.getEvents() != null ? List.copyOf(newCompilation.getEvents()) : List.of())
                .build();
    }

    public static CompilationDto toDto(Compilation compilation, List<EventShortInfo> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(events == null ? List.of() : events.stream()
                        .map(CompilationMapper::toEventShortDto)
                        .toList())
                .build();
    }

    private static EventShortDto toEventShortDto(EventShortInfo event) {
        return EventShortDto.builder()
                .id(event.getId())
                .title(event.getTitle())
                .annotation(event.getAnnotation())
                .eventDate(event.getEventDate())
                .paid(event.isPaid())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .category(CategoryDto.builder()
                        .id(event.getCategoryId())
                        .name(event.getCategoryName())
                        .build())
                .initiator(UserShortDto.builder()
                        .id(event.getInitiatorId())
                        .name(event.getInitiatorName())
                        .build())
                .build();
    }
}