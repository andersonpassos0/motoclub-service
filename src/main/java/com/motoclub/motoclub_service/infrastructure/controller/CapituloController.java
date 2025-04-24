package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.service.CapituloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Capítulo", description = "Operações relacionadas aos capítulos do Moto Clube")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("v1/api/capitulo")
public class CapituloController {

    private final CapituloService service;

    @PostMapping
    @Operation(summary = "Cria um novo Capítulo", description = "Cria um novo Capítulo vinculado a um MotoClubeGeral existente.")
    public ResponseEntity<CapituloResponseDTO> create(@RequestBody @Valid CapituloRequestDTO request){
        CapituloResponseDTO response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca capítulo por ID", description = "Retorna os dados do capítulo correspondente ao ID informado.")
    public  ResponseEntity<CapituloResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Lista todos os capítulos", description = "Retorna uma lista paginada de todos os capítulos cadastrados.")
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
    @Operation(summary = "Atualiza um capítulo existente", description = "Atualiza os dados de um capítulo com base no ID informado.")
    public ResponseEntity<CapituloResponseDTO> update(@PathVariable Long id,
                                                      @RequestBody @Valid CapituloRequestDTO request){
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um capítulo", description = "Remove o capítulo correspondente ao ID informado.")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}
