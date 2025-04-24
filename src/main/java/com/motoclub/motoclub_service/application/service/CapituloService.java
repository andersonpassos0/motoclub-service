package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CapituloService {

    CapituloResponseDTO create(@Valid CapituloRequestDTO request);
    CapituloResponseDTO findById(Long id);
    Page<CapituloResponseDTO> findAll(Pageable pageable);
    CapituloResponseDTO update(Long id, @Valid CapituloRequestDTO request);
    void delete(Long id);

}
