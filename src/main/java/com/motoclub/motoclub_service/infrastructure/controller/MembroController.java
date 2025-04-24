package com.motoclub.motoclub_service.infrastructure.controller;

import com.motoclub.motoclub_service.application.dto.MembroRequestDTO;
import com.motoclub.motoclub_service.application.dto.MembroResponseDTO;
import com.motoclub.motoclub_service.application.service.MembroService;
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

@RestController
@RequestMapping("/v1/api/membro")
@Tag(name = "Membro", description = "Operações relacionadas aos membros do Moto Clube")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class MembroController {

    private final MembroService membroService;

    @PostMapping
    @Operation(summary = "Cria um novo Membro", description = "Cria um novo membro vinculado a um Capitulo")
    public ResponseEntity<MembroResponseDTO> create (@RequestBody @Valid MembroRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED).body(membroService.create(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um membro pelo ID", description = "Retorna um membro através do ID enviado")
    public ResponseEntity<MembroResponseDTO> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(membroService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Busca lista de Membros", description = "Retorna lista paginada de membros")
    public ResponseEntity<Page<MembroResponseDTO>> findAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "nome") String sortBy,
        @RequestParam(defaultValue = "ASC") String sortOrder) {

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        return ResponseEntity.status(HttpStatus.OK).body(membroService.findAll(pageable));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza membro", description = "Atualiza informações de membro")
    public ResponseEntity<MembroResponseDTO> update (@PathVariable Long id,
                                                     @RequestBody @Valid MembroRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK).body(membroService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um membro", description = "Exclui um membro pela ID informada")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        membroService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
