package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.service.CapituloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/capitulo")
public class CapituloController {

    private final CapituloService service;

    @PostMapping
    public ResponseEntity<CapituloResponseDTO> create(@RequestBody @Valid CapituloRequestDTO request){
        CapituloResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<CapituloResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<CapituloResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nomeCapitulo") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CapituloResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody @Valid CapituloRequestDTO request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
