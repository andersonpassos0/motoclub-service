package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/motoclube")
@RequiredArgsConstructor
public class MotoClubeGeralController {

    private final MotoClubeGeralService service;

    @PostMapping
    public ResponseEntity<MotoClubeGeralResponseDTO> create (@RequestBody @Valid MotoClubeGeralRequestDTO request) {
        return ResponseEntity.ok(service.create(request));
    }
}
