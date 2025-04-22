package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

public interface CapituloService {

    CapituloResponseDTO create(@Valid CapituloRequestDTO request);
}
