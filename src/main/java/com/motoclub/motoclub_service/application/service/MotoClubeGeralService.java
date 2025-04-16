package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

public interface MotoClubeGeralService {

    MotoClubeGeralResponseDTO create(MotoClubeGeralRequestDTO request, MultipartFile file) throws IOException;
    MotoClubeGeralResponseDTO findById(Long id);
    Page<MotoClubeGeralResponseDTO> findAll(Pageable pageable);
    MotoClubeGeralResponseDTO update(Long id, @Valid MotoClubeGeralRequestDTO request, MultipartFile file) throws IOException;
    void delete(Long id);
}
