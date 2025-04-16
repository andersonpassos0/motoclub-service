package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;

public interface MotoClubeGeralService {
    MotoClubeGeralResponseDTO create(MotoClubeGeralRequestDTO request);
}
