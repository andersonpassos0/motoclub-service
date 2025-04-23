package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Moto Clube Geral", description = "Operações com o MotoClubeGeral")
@RequiredArgsConstructor
public class MotoClubeGeralController {

    private final MotoClubeGeralService service;

    @PostMapping
    @Operation(summary = "Cria um novo MotoClubeGeral", description = "Cria um novo MotoClubeGeral com os dados informados e upload de logomarca.")
    public ResponseEntity<MotoClubeGeralResponseDTO> create (
            @RequestPart("data") @Valid MotoClubeGeralRequestDTO request,
            @RequestPart("file") MultipartFile file) throws IOException {

        return ResponseEntity.ok(service.create(request, file));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca MotoClubeGeral por ID", description = "Retorna os dados do MotoClubeGeral com base no ID informado.")
    public ResponseEntity<MotoClubeGeralResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os MotoClubeGeral", description = "Retorna uma lista paginada de MotoClubesGerais, ordenada conforme parâmetros.")
    public ResponseEntity<Page<MotoClubeGeralResponseDTO>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(sortOrder.toUpperCase());
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um MotoClubeGeral", description = "Atualiza os dados de um MotoClubeGeral, com opção de atualizar a logomarca.")
    public ResponseEntity<MotoClubeGeralResponseDTO> update(
            @PathVariable Long id,
            @RequestPart("data") @Valid MotoClubeGeralRequestDTO request,
            @RequestPart(value = "file", required = false) MultipartFile file) throws IOException {

        return ResponseEntity.ok(service.update(id, request, file));
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Remove um MotoClubeGeral", description = "Exclui um MotoClubeGeral com base no ID informado.")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
