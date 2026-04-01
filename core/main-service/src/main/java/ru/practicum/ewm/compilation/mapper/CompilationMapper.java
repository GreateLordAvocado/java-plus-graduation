package ru.practicum.ewm.compilation.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.compilation.dto.CompilationDto;
import ru.practicum.ewm.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.compilation.model.Compilation;
import ru.practicum.ewm.event.dto.EventShortDto;

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

    public static CompilationDto toDto(Compilation compilation, List<EventShortDto> events) {
        return CompilationDto.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.isPinned())
                .events(events != null ? events : List.of())
                .build();
    }
}