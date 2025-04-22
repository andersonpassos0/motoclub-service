package com.motoclub.motoclub_service.infrastructure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.service.CapituloService;
import com.motoclub.motoclub_service.config.TestSecurityConfig;
import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
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
                "", "Rua Teste", "São Paulo", "SP", "00000-000", "teste@teste.com",
                DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 1L);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        System.out.println("🛠️ JSON de resposta: " + contentAsString);

//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("nomeCapitulo"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("O nome é obrigatório"));
    }

//    @Test
//    @DisplayName("POST /v1/api/capitulo - should return error when MotoClubeGeral ID does not exist")
//    void shouldReturnErrorWhenMotoClubeGeralIdDoesNotExist() throws Exception {
//
//        CapituloRequestDTO request = new CapituloRequestDTO(
//                "Capítulo Teste", "Rua Teste", "São Paulo", "SP", "00000-000", "teste@teste.com",
//                DiaSemana.SEGUNDA_FEIRA, "2025-04-22", 999L);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/capitulo")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(request)))
//                .andExpect(status().isNotFound())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].field").value("motoClubeGeralId"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.errors[0].message").value("MotoClubeGeral não encontrado"));
//    }

}
