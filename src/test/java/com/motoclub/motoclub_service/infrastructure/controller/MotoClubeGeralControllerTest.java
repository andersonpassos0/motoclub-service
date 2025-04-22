package com.motoclub.motoclub_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.service.MotoClubeGeralService;
import com.motoclub.motoclub_service.config.TestSecurityConfig;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class MotoClubeGeralControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    private MotoClubeGeralService service;

    @Test
    @DisplayName("POST /v1/api/motoclube - should create MotoClubeGeral")
    void shouldCreateMotoClubeGeral() throws Exception {

        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Moto Clube Teste");
        request.setCnpj("12.345.678/0001-00");
        request.setEnderecoSede("Rua A, 123");
        request.setCidade("São Paulo");
        request.setUf("SP");
        request.setEmail("email@teste.com");
        request.setTelefone("11999999999");
        request.setImagemLogoBase64("base64");
        request.setDataRegistroClube(LocalDate.of(2025, 1, 1));

        byte[] imageBase64 = "base64".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "logomarca.jpg", MediaType.IMAGE_JPEG_VALUE, imageBase64);
        MockMultipartFile json = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));
        MotoClubeGeralResponseDTO response = new MotoClubeGeralResponseDTO(
                1l, request.getNome(), request.getCnpj(), request.getEnderecoSede(), request.getEmail(),
                request.getImagemLogoBase64(), request.getDataRegistroClube().toString());

        when(service.create(any(), any())).thenReturn(response);

        mockMvc.perform(multipart("/v1/api/motoclube")
                        .file(json)
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nome").value("Moto Clube Teste"))
                .andExpect(jsonPath("$.cnpj").value("12.345.678/0001-00"))
                .andExpect(jsonPath("$.enderecoSede").value("Rua A, 123"))
                .andExpect(jsonPath("$.email").value("email@teste.com"))
                .andExpect(jsonPath("$.imagemLogoBase64").isNotEmpty())
                .andExpect(jsonPath("$.dataRegistroClube").value("2025-01-01"));
    }

    @Test
    @DisplayName("POST /v1/api/motoclube - should return 400 BadRequest when input is invalid")
    void shouldReturnBadRequestWhenMotoClubeGeralIsInvalid() throws Exception {

        String invalidJson = """
        {
            "cnpj": "12.345.678/0001-00",
            "enderecoSede": "Rua A, 123",
            "cidade": "São Paulo",
            "uf": "SP",
            "email": "email@teste.com",
            "telefone": "11999999999",
            "imagemLogoBase64": "base64",
            "dataRegistroClube": "2025-01-01"
        }
        """;

        MockMultipartFile json = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, invalidJson.getBytes());
        MockMultipartFile file = new MockMultipartFile("file","logomarca.jpg", MediaType.IMAGE_JPEG_VALUE, "base64".getBytes());

        mockMvc.perform(multipart("/v1/api/motoclube")
                .file(json)
                .file(file)
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /v1/api/motoclube - should return 500 Internal Server Erros when service fails")
    void shouldReturnInternalServerErrorWhenServiceThrowsException() throws Exception {
        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Clube com Erro");
        request.setCnpj("00.000.000/0001-00");
        request.setEnderecoSede("Rua Erro, 404");
        request.setCidade("Cidade Bug");
        request.setUf("SP");
        request.setEmail("erro@bug.com");
        request.setTelefone("11999999999");
        request.setImagemLogoBase64("base64");
        request.setDataRegistroClube(LocalDate.of(2025, 1, 1));

        MockMultipartFile file = new MockMultipartFile("file", "logomarca.jpg", MediaType.IMAGE_JPEG_VALUE, "base64".getBytes());
        MockMultipartFile json = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

        Mockito.when(service.create(any(), any())).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(multipart("/v1/api/motoclube")
                    .file(json)
                    .file(file)
                    .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("GET /v1/api/motoclube/{id} - should return MotoClube Geral by ID")
    void shouldReturnMotoClubeGeralById() throws Exception {
        Long id = 1L;

        MotoClubeGeralResponseDTO response = new MotoClubeGeralResponseDTO(id, "Moto Clube Teste", "01.234.567/0001-00",
                "Rua A, 123", "teste@email.com", "base64", LocalDate.now().toString());

        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/v1/api/motoclube/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Moto Clube Teste"))
                .andExpect(jsonPath("$.cnpj").value("01.234.567/0001-00"))
                .andExpect(jsonPath("$.email").value("teste@email.com"));
    }

    @Test
    @DisplayName("GET /v1/api/motoclube/{id} - should return 404 if not found")
    void shouldReturnNotFoudWhenMotoClubeGeralDoesNotExist() throws Exception {
        Long id = 99L;

        when(service.findById(id)).thenThrow(new EntityNotFoundException("Moto Clube não encontrado"));

        mockMvc.perform(get("/v1.api.motoclube/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /v1/api/motoclube - shoud return paginated MotoClube list")
    void shouldReturnPaginatedMotoClubeList() throws  Exception {

        MotoClubeGeralResponseDTO response1 = new MotoClubeGeralResponseDTO(
                1L, "Moto Clube 1", "01.234.567/0001-02", "Rua A, 123", "teste1@email.com",
                "base64", LocalDate.of(2025, 1, 1).toString());

        MotoClubeGeralResponseDTO response2 = new MotoClubeGeralResponseDTO(
                1L, "Moto Clube 2", "98.765.432/0001-02", "Rua B, 321", "teste2@email.com",
                "base64", LocalDate.of(2025, 1, 1).toString());

        List<MotoClubeGeralResponseDTO> lista = List.of(response1, response2);

        PageRequest pageable = PageRequest.of(0, 10, Sort.by("nome").ascending());
        PageImpl<MotoClubeGeralResponseDTO> page = new PageImpl<>(lista, pageable, lista.size());

        when(service.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/v1/api/motoclube")
                .param("page", "0")
                .param("size", "10")
                .param("sortBy", "nome")
                .param("sortOrder", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(2))
                .andExpect(jsonPath("$.content[0].nome").value("Moto Clube 1"))
                .andExpect(jsonPath("$.content[1].nome").value("Moto Clube 2"));
    }

    @Test
    @DisplayName(" GET /v1/api/motoclube - shoud return 500 when service throws exception")
    void shouldReturnInternalServerErrorWhenFindAllThrowsException() throws  Exception {

        when(service.findAll(any())).thenThrow(new RuntimeException("Erro inesperado"));

        mockMvc.perform(get("/v1/api/motoclube")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sortBy", "nome")
                    .param("sortOrder", "ASC"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Erro inesperado"));
    }

    @Test
    @DisplayName("PUT /v1/api/motoclube/{id} should update MotoClubeGeral")
    void shouldUpdateMotoClubeGeral() throws Exception {
        Long id = 1L;

        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Moto Clube Atualizado");
        request.setCnpj("98.765.432/0001-00");
        request.setEnderecoSede("Rua B, 456");
        request.setCidade("Rio de Janeiro");
        request.setUf("RJ");
        request.setEmail("atualiza@email.com");
        request.setTelefone("11999999999");
        request.setImagemLogoBase64("base64");
        request.setDataRegistroClube(LocalDate.of(2025, 1, 1));

        byte[] imageBytes = "base64".getBytes();
        MockMultipartFile file = new MockMultipartFile("file", "nova_logo.jpg", MediaType.IMAGE_JPEG_VALUE, imageBytes);
        MockMultipartFile json = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));

        MotoClubeGeralResponseDTO response = new MotoClubeGeralResponseDTO(
                id, request.getNome(), request.getCnpj(), request.getEnderecoSede(), request.getEmail(),
                request.getImagemLogoBase64(), request.getDataRegistroClube().toString());

        when(service.update(eq(id), any(), any())).thenReturn(response);

        mockMvc.perform(multipart("/v1/api/motoclube/{id}", id)
                .file(json)
                .file(file)
                .with(req -> { req.setMethod("PUT"); return req; })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nome").value("Moto Clube Atualizado"))
                .andExpect(jsonPath("$.email").value("atualiza@email.com"));
    }

    @Test
    @DisplayName("PUT /v1/api/motoclube/{id} - should return 500 when service throws exception")
    void shouldReturnInternalServerErrorWhenUpdateFails() throws Exception {

        Long id = 1L;

        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Falha");
        request.setCnpj("01.234.567/0001-00");
        request.setEnderecoSede("Rua A, 123");
        request.setCidade("São Paulo");
        request.setUf("SP");
        request.setEmail("teste@email.com");
        request.setTelefone("11999999999");
        request.setImagemLogoBase64("base64");
        request.setDataRegistroClube(LocalDate.now());

        MockMultipartFile json = new MockMultipartFile("data", "", MediaType.APPLICATION_JSON_VALUE, objectMapper.writeValueAsBytes(request));
        MockMultipartFile file = new MockMultipartFile("file", "file.jpg", MediaType.IMAGE_JPEG_VALUE, "base64".getBytes());

        when(service.update(eq(id), any(), any())).thenThrow(new RuntimeException("Erro no update"));

        mockMvc.perform(multipart("/v1/api/motoclube/{id}", id)
                .file(json)
                .file(file)
                .with(req -> {req.setMethod("PUT"); return req; })
                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("DELETE /v1/api/motoclube/{id} = should delete MotoClubeGeral")
    void shouldDeleteMotoClubeGeral() throws Exception {
        Long id = 1L;
        mockMvc.perform(delete("/v1/api/motoclube/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /v1/api/motoclube/{id} - should return 500 when service throw Exceptions")
    void shouldReturnInternalServerErrorWhenDeleteFails() throws Exception {
        Long id = 99L;

        doThrow(new RuntimeException("Erro ao deletar")).when(service).delete(id);

        mockMvc.perform(delete("/v1/api/motoclube/{id}", id))
                .andExpect(status().isInternalServerError());
    }



}

