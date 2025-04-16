package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/v1/api/motoclube")
@RequiredArgsConstructor
public class MotoClubeGeralController {

    private final MotoClubeGeralService service;

    @PostMapping
    public ResponseEntity<MotoClubeGeralResponseDTO> create (
            @RequestPart("data") @Valid MotoClubeGeralRequestDTO request,
            @RequestPart("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(service.create(request, file));
    }
}
