package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.mapper.CapituloMapper;
import com.motoclub.motoclub_service.application.service.impl.CapituloServiceImpl;
import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import com.motoclub.motoclub_service.domain.repository.CapituloRepository;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import com.motoclub.motoclub_service.infrastructure.exceptions.RecursoNaoEncontradoException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CapituloServiceImplTest {

    @Mock
    private CapituloRepository capituloRepository;

    @Mock
    private MotoClubeGeralRepository motoClubeGeralRepository;

    @Mock
    private CapituloMapper capituloMapper;

    @InjectMocks
    private CapituloServiceImpl capituloService;

    private CapituloRequestDTO request;
    private Capitulo entity;
    private CapituloResponseDTO response;
    private MotoClubeGeral motoClubeGeral;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        request = new CapituloRequestDTO("Capítulo A", "Rua A", "Cidade A", "SP", "00000-000", "a@email.com", DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 1L);
        entity = new Capitulo(1L, "Capítulo A", "Rua A", "Cidade A", "SP", "00000-000", "a@email.com", DiaSemana.SEGUNDA_FEIRA, LocalDate.parse("2025-04-22").toString(), null);
        response = new CapituloResponseDTO(1L, "Capítulo A", "Rua A", "Cidade A", "SP", "00000-000", "a@email.com", DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 1L);
        motoClubeGeral = new MotoClubeGeral();
    }

    @Test
    void shouldCreateCapituloSuccessfully() {
        when(motoClubeGeralRepository.findById(1L)).thenReturn(Optional.of(motoClubeGeral));
        when(capituloMapper.toEntity(request)).thenReturn(entity);
        when(capituloRepository.save(entity)).thenReturn(entity);
        when(capituloMapper.toDto(entity)).thenReturn(response);

        CapituloResponseDTO result = capituloService.create(request);

        assertNotNull(result);
        assertEquals("Capítulo A", result.nomeCapitulo());
        verify(capituloRepository).save(entity);
    }

    @Test
    void shouldThrowWhenMotoClubeGeralNotFound() {
        when(motoClubeGeralRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> capituloService.create(request));
    }

    @Test
    void shouldFindCapituloById() {
        when(capituloRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(capituloMapper.toDto(entity)).thenReturn(response);

        CapituloResponseDTO result = capituloService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.id());
    }

    @Test
    void shouldThrowWhenCapituloNotFoundById() {
        when(capituloRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> capituloService.findById(1L));
    }

    @Test
    void shouldReturnPaginatedListOfCapitulos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Capitulo> page = new PageImpl<>(List.of(entity));
        when(capituloRepository.findAll(pageable)).thenReturn(page);
        when(capituloMapper.toDto(entity)).thenReturn(response);

        Page<CapituloResponseDTO> result = capituloService.findAll(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertEquals("Capítulo A", result.getContent().get(0).nomeCapitulo());
    }

    @Test
    void shouldUpdateCapituloSuccessfully() {
        Capitulo updated = new Capitulo(1L, "Capítulo A", "Rua A", "Cidade A", "SP", "00000-000", "a@email.com", DiaSemana.SEGUNDA_FEIRA, LocalDate.parse("2025-04-22").toString(), null);

        when(capituloRepository.findById(1L)).thenReturn(Optional.of(entity));
        when(capituloMapper.toEntity(request)).thenReturn(updated);
        when(capituloRepository.save(updated)).thenReturn(updated);
        when(capituloMapper.toDto(updated)).thenReturn(response);

        CapituloResponseDTO result = capituloService.update(1L, request);

        assertNotNull(result);
        assertEquals("Capítulo A", result.nomeCapitulo());
    }

    @Test
    void shouldThrowWhenUpdatingNonexistentCapitulo() {
        when(capituloRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> capituloService.update(1L, request));
    }

    @Test
    void shouldDeleteCapituloSuccessfully() {
        when(capituloRepository.findById(1L)).thenReturn(Optional.of(entity));

        capituloService.delete(1L);

        verify(capituloRepository).delete(entity);
    }

    @Test
    void shouldThrowWhenDeletingNonexistentCapitulo() {
        when(capituloRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> capituloService.delete(1L));
    }
}
