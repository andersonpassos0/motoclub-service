package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.MembroRequestDTO;
import com.motoclub.motoclub_service.application.dto.MembroResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MembroService {
    MembroResponseDTO create(MembroRequestDTO request);
    MembroResponseDTO findById(Long id);
    Page<MembroResponseDTO> findAll(Pageable pageable);
    MembroResponseDTO update(Long id, @Valid MembroRequestDTO request);
    void delete(Long id);
}
