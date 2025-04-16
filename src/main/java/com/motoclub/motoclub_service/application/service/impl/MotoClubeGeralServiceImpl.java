package com.motoclub.motoclub_service.application.service.impl;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.mapper.MotoClubeGeralMapper;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MotoClubeGeralServiceImpl implements MotoClubeGeralService {

    private final MotoClubeGeralRepository repository;
    private final MotoClubeGeralMapper mapper;

    @Override
    public MotoClubeGeralResponseDTO create(MotoClubeGeralRequestDTO request) {
        MotoClubeGeral entity = mapper.toEntity(request);
        entity.setDataCriacaoRegistro(LocalDateTime.now());
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }
}
