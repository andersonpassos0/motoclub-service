package com.motoclub.motoclub_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.dto.MembroRequestDTO;
import com.motoclub.motoclub_service.application.dto.MembroResponseDTO;
import com.motoclub.motoclub_service.application.service.MembroService;
import com.motoclub.motoclub_service.config.TestSecurityConfig;
import com.motoclub.motoclub_service.domain.enums.StatusMembroEnum;
import com.motoclub.motoclub_service.infrastructure.exceptions.RecursoNaoEncontradoException;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
public class MembroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MembroService membroService;

    private MembroRequestDTO createValidMembroRequest() {
        return new MembroRequestDTO(
                "João Silva", "12345678900", "11999998888", "Rua A, 123", "Centro", "São Paulo", "SP",
                "05858-001","teste@email.com", "123456789", "AB",
                LocalDate.of(2030, 1, 1), LocalDate.of(1990, 1, 1), "A+",
                "Maria Silva", "11977776666", LocalDate.of(2024, 1, 1), StatusMembroEnum.ATIVO,
                true, 1L, List.of(2L, 3L));

    }

    private MembroResponseDTO createValidMembroResponse(){
        return new MembroResponseDTO(
                1L, "João Silva", "1234567800", "11999998888", "teste@email.com", "Rua A, 123", "Centro",
                "São Paulo", "SP", "05858-001", "123456789", "AB",
                LocalDate.of(2030, 1, 1), LocalDate.of(1990, 1, 1), "A+",
                "Maria Silva", "11977776666", LocalDate.of(2024, 1, 1), StatusMembroEnum.ATIVO,
                true, "AMM - Eunápolis", List.of(), List.of("Diretor de Capítulo", "Tesoureiro")
        );
    }

    @Test
    @DisplayName("POST /v1/api/membro - should create Membro successfully")
    void shouldCreateMembroSuccessfully() throws Exception {

        when(membroService.create(any())).thenReturn(createValidMembroResponse());

        mockMvc.perform(post("/v1/api/membro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createValidMembroRequest())))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("João Silva"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("1234567800"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.telefone").value("11999998888"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("teste@email.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capituloNome").value("AMM - Eunápolis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargosNome[0]").value("Diretor de Capítulo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargosNome[1]").value("Tesoureiro"));
    }

    @Test
    @DisplayName("POST /v1/api/membro - should return error when required field is missing")
    void shouldReturnErrorWhenRequiredFieldIsMissing() throws Exception {
        MembroRequestDTO request = new MembroRequestDTO(
                "", "12345678900", "11999998888", "Rua A, 123", "Centro", "São Paulo", "SP",
                "05858-001","teste@email.com", "123456789", "AB",
                LocalDate.of(2030, 1, 1), LocalDate.of(1990, 1, 1), "A+",
                "Maria Silva", "11977776666", LocalDate.of(2024, 1, 1), StatusMembroEnum.ATIVO,
                true, 1L, List.of(2L));

        mockMvc.perform(post("/v1/api/membro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("nome"))
                .andExpect(jsonPath("$.errors[0].message").value("O nome é obrigatório"));
    }

    @Test
    @DisplayName("POST /v1/api/membro - should return error when Capitulo ID does not Exist")
    void shouldReturnErrorWhenCapituloidDoesNotExist() throws Exception {
        MembroRequestDTO request = new MembroRequestDTO(
                "João Silva", "12345678900", "11999998888", "Rua A, 123", "Centro", "São Paulo", "SP",
                "05858-001","teste@email.com", "123456789", "AB",
                LocalDate.of(2030, 1, 1), LocalDate.of(1990, 1, 1), "A+",
                "Maria Silva", "11977776666", LocalDate.of(2024, 1, 1), StatusMembroEnum.ATIVO,
                true, 999L, List.of(2L, 3L));

        when(membroService.create(any())).thenThrow(new RecursoNaoEncontradoException("capituloId", "Capítulo não encontrado"));

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/membro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("capituloId"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("Capítulo não encontrado"));
    }

    @Test
    @DisplayName("GET /v1/api/membro/{id} - should return Membro by ID")
    void shouldReturnMembroByIdSuccessfully() throws Exception {
        Long id = 1L;

        MembroResponseDTO response = createValidMembroResponse();

        when(membroService.findById(id)).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/membro/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capituloNome").value("AMM - Eunápolis"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cidade").value("São Paulo"));
    }
}
