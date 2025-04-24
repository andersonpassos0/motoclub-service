package com.motoclub.motoclub_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.service.CapituloService;
import com.motoclub.motoclub_service.config.TestSecurityConfig;
import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import com.motoclub.motoclub_service.infrastructure.exceptions.RecursoNaoEncontradoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class CapituloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CapituloService capituloService;

    @Test
    @DisplayName("POST /v1/api/capitulo - should create Capitulo")
    void shouldCreateCapituloSuccessfully() throws Exception {

        CapituloRequestDTO request = new CapituloRequestDTO(
                "Capítulo Teste", "Rua Teste", "São Paulo", "SP", "00000-000", "teste@teste.com",
                DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 1L);

        CapituloResponseDTO response = new CapituloResponseDTO(
                1L, "Capítulo Teste", "Rua Teste", "São Paulo", "SP", "00000-000",
                "teste@teste.com", DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 1L);

        when(capituloService.create(any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCapitulo").value("Capítulo Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.enderecoSede").value("Rua Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cidade").value("São Paulo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.uf").value("SP"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@teste.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataCriacaoCapitulo").value("2025-04-22"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataCriacaoCapitulo").value("2025-04-22"));
    }

    @Test
    @DisplayName("POST /v1/api/capitulo - should return error when a required field is missing")
    void shouldReturnErrorWhenRequiredFieldIsMissing() throws Exception {

        CapituloRequestDTO request = new CapituloRequestDTO(
                "", "Rua A, 123", "São Paulo", "SP", "45820-000", "teste@email.com",
                DiaSemana.SEGUNDA_FEIRA, "2025-01-01", 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("nomeCapitulo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("O nome é obrigatório"));
    }

    @Test
    @DisplayName("POST /v1/api/capitulo - should return error when MotoClubeGeral ID does not exist")
    void shouldReturnErrorWhenMotoClubeGeralIdDoesNotExist() throws Exception {

        CapituloRequestDTO request = new CapituloRequestDTO(
                "Capítulo Teste", "Rua A, 123", "São Paulo", "SP", "45820-000", "teste@email.com",
                DiaSemana.SEGUNDA_FEIRA, "2025-01-01", 999L);

        when(capituloService.create(any())).thenThrow(new RecursoNaoEncontradoException("motoClubeGeralId", "MotoClubeGeral não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("motoClubeGeralId"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("MotoClubeGeral não encontrado"));
    }

    @Test
    @DisplayName("GET /v1/api/capitulo/{id} - should return Capitulo by ID")
    void shouldReturnCapituloByIdSuccessfully() throws Exception {
        Long id = 1L;

        CapituloResponseDTO response = new CapituloResponseDTO(
                id, "Capitulo Teste", "Rua A, 123", "São Paulo", "SP", "45820-000",
                "teste@email.com", DiaSemana.SEGUNDA_FEIRA, "2025-01-01", 1L);

        when(capituloService.findById(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/capitulo/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCapitulo").value("Capitulo Teste"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cidade").value("São Paulo"));
    }

    @Test
    @DisplayName("GET /v1/api/capitulo/{id} - should return 404 when Capitulo not found")
    void shouldReturnNotFoundWhenCapituloNotFound() throws Exception {
        Long id = 999L;

        when(capituloService.findById(id)).thenThrow(new RecursoNaoEncontradoException("id", "Capítulo não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/capitulo/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Capítulo não encontrado"));
    }

    @Test
    @DisplayName("GET /v1/api/capitulo - should return paginated list of Capitulos")
    void shouldReturnPaginatedListOfCapitulos() throws Exception {
        CapituloResponseDTO capitulo1 = new CapituloResponseDTO(
                1L, "Capítulo A", "Rua A", "São Paulo", "SP", "45820-000",
                "testea@email.com", DiaSemana.SEGUNDA_FEIRA, "2025-01-01", 1L);

        CapituloResponseDTO capitulo2 = new CapituloResponseDTO(
                2L, "Capítulo B", "Rua B", "Rio de Janeiro", "RJ", "45820-000",
                "testeb@email.com", DiaSemana.QUARTA_FEIRA, "2025-01-01", 1L);

        Page<CapituloResponseDTO> page = new PageImpl<>(List.of(capitulo1, capitulo2), PageRequest.of(0, 10), 2);

        when(capituloService.findAll(any(Pageable.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/capitulo")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "nomeCapitulo")
                .param("sortOrder", "ASC")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].nomeCapitulo").value("Capítulo A"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].nomeCapitulo").value("Capítulo B"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(1));
    }

    @Test
    @DisplayName("GET /v1/api/capitulo - should return empty page when no Capitulos")
    void shouldReturnEmptyPageWhenNoCapitulos() throws Exception {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("nomeCapitulo").ascending());
        Page<CapituloResponseDTO> emptyPage = new PageImpl<>(List.of(), pageable, 0);

        when(capituloService.findAll(any(Pageable.class))).thenReturn(emptyPage);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/capitulo")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "nomeCapitulo")
                        .param("sortOrder", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(0));
    }

    @Test
    @DisplayName("PUT /v1/api/capitulo/{id} - should update Capitulo")
    void shouldUpdateCapituloSuccessfully() throws Exception {
        Long id = 1L;

        CapituloRequestDTO request = new CapituloRequestDTO(
                "Capítulo Atualizado", "Rua A, 123", "São Paulo", "SP", "45820-000", "atualizado@email.com",
                DiaSemana.QUARTA_FEIRA, "2020-01-01", 2L);

        CapituloResponseDTO response = new CapituloResponseDTO(
                id, "Capítulo Atualizado", "Rua A, 123", "São Paulo", "SP", "45820-000", "atualizado@email.com",
                DiaSemana.QUARTA_FEIRA, "2020-01-01", 2L);

        when(capituloService.update(eq(id), any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/capitulo/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nomeCapitulo").value("Capítulo Atualizado"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cidade").value("São Paulo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataCriacaoCapitulo").value("2020-01-01"));
    }

    @Test
    @DisplayName("PUT /v1/api/capitulo/{id} - should return 404 when Capitulo not found")
    void shouldReturn404WhenCapituloNotFoundOnUpdate() throws Exception {

        Long id = 999L;

        CapituloRequestDTO request = new CapituloRequestDTO(
                "Capítulo Atualizado", "Rua A, 123", "São Paulo", "SP", "45820-000", "atualizado@email.com",
                DiaSemana.QUARTA_FEIRA, "2025-01-01", 2L);

        when(capituloService.update(eq(id), any())).thenThrow(new RecursoNaoEncontradoException("id", "Capítulo não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/capitulo/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Capítulo não encontrado"));
    }

    @Test
    @DisplayName("DELETE /v1/api/capitulo/{id} - should delete Capitulo")
    void shouldDeleteCapituloSuccessfully() throws Exception {
        Long id = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/capitulo/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /v1/api/capitulo/{id} - should return 404 when Capitulo not found")
    void shouldReturn404WhenCapituloNotFoundOnDelete() throws Exception {
        Long id = 999L;

        doThrow(new RecursoNaoEncontradoException("id", "Capítulo não encontrado"))
                .when(capituloService).delete(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/capitulo/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("id"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Capítulo não encontrado"));
    }
}
