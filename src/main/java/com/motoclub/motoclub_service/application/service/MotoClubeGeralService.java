package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface MotoClubeGeralService {
    MotoClubeGeralResponseDTO create(MotoClubeGeralRequestDTO request, MultipartFile file) throws IOException;
}
