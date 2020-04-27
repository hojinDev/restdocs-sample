package com.example.demo.service;

import com.example.demo.domain.Unit;
import com.example.demo.domain.UnitRepository;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.dto.UnitCreateDto;
import com.example.demo.service.dto.UnitDto;
import com.example.demo.service.dto.UnitUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UnitService {

    private final UnitRepository unitRepository;

    public List<UnitDto> findAll() {

        return unitRepository.findAll().stream()
                .map(UnitDto::of)
                .collect(Collectors.toList());
    }

    public UnitDto findById(Long id) {

        return unitRepository.findById(id)
                .map(UnitDto::of)
                .orElseThrow(NotFoundException::new);
    }

    public UnitDto add(UnitCreateDto create) {

        Unit target = create.toEntity();
        Unit created = unitRepository.save(target);

        return UnitDto.of(created);
    }

    public void delete(Long id) {

        Unit unit = unitRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        unitRepository.delete(unit);
    }

    @Transactional
    public UnitDto update(Long id, UnitUpdateDto update) {

        Unit unit = unitRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        update.apply(unit);

        return UnitDto.of(unit);
    }
}
