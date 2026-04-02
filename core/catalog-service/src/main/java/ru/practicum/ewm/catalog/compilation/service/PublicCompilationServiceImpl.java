package ru.practicum.ewm.catalog.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.common.exception.NotFoundException;
import ru.practicum.ewm.compilation.contract.CompilationEventProvider;
import ru.practicum.ewm.catalog.compilation.dto.CompilationDto;
import ru.practicum.ewm.catalog.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.catalog.compilation.model.Compilation;
import ru.practicum.ewm.catalog.compilation.repository.CompilationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublicCompilationServiceImpl implements PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationEventProvider compilationEventProvider;

    @Override
    public CompilationDto find(long id) {
        final Compilation compilation = compilationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Compilation with id=" + id + " was not found"));
        return CompilationMapper.toDto(
                compilation,
                compilationEventProvider.getShortEventsByIds(compilation.getEventIds())
        );
    }

    @Override
    public List<CompilationDto> findEventCompilations(Boolean pinned, int from, int size) {
        return compilationRepository.findCompilationsByPinned(pinned, PageRequest.of(from, size))
                .stream()
                .map(compilation -> CompilationMapper.toDto(
                        compilation,
                        compilationEventProvider.getShortEventsByIds(compilation.getEventIds())
                ))
                .toList();
    }
}