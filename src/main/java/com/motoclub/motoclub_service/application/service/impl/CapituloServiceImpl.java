package com.motoclub.motoclub_service.application.service.impl;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.mapper.CapituloMapper;
import com.motoclub.motoclub_service.application.service.CapituloService;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import com.motoclub.motoclub_service.domain.repository.CapituloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapituloServiceImpl implements CapituloService {

    private final CapituloRepository capituloRepository;
    private final CapituloMapper capituloMapper;

    @Override
    public CapituloResponseDTO create(CapituloRequestDTO request) {
        Capitulo capitulo = capituloMapper.toEntity(request);
        Capitulo capituloSalvo = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloSalvo);
    }
}
