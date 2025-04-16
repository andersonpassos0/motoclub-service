package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    @GetMapping("/{id}")
    public ResponseEntity<MotoClubeGeralResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<MotoClubeGeralResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.findAll(pageable));
    }
}
