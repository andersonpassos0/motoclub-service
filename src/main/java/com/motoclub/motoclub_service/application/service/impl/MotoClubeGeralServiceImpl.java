package com.motoclub.motoclub_service.application.service.impl;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.mapper.MotoClubeGeralMapper;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotoClubeGeralServiceImpl implements MotoClubeGeralService {

    private final MotoClubeGeralRepository repository;
    private final MotoClubeGeralMapper mapper;

    @Override
    public MotoClubeGeralResponseDTO create(MotoClubeGeralRequestDTO request, MultipartFile file) throws IOException {

        request.setImagemLogoBase64(Base64.getEncoder().encodeToString(file.getBytes()));

        MotoClubeGeral entity = mapper.toEntity(request);
        entity.setDataCriacaoRegistro(LocalDateTime.now());
        repository.save(entity);
        return mapper.toResponseDTO(entity);
    }

    @Override
    public MotoClubeGeralResponseDTO findById(Long id) {
        MotoClubeGeral entity = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Moto clube com ID " + id + " não encontrado!"));
        return mapper.toResponseDTO(entity);
    }

   @Override
    public Page<MotoClubeGeralResponseDTO> findAll(Pageable pageable) {
        Page<MotoClubeGeral> motoclubegeralList = repository.findAll(pageable);

        return motoclubegeralList.map(mapper::toResponseDTO);
    }
}
