package com.motoclub.motoclub_service.application.service;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.application.mapper.MotoClubeGeralMapper;
import com.motoclub.motoclub_service.application.service.impl.MotoClubeGeralServiceImpl;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MotoClubeGeralServiceImplTest {

    @InjectMocks
    private MotoClubeGeralServiceImpl service;

    @Mock
    private MotoClubeGeralRepository repositiory;

    @Mock
    private MotoClubeGeralMapper mapper;

    @Test
    void shouldCreateMotoClubeSuccessfully() throws IOException {

        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Clube Teste");
        request.setCnpj("12345678901234");
        request.setEnderecoSede("Rua A");
        request.setCidade("São Paulo");
        request.setUf("SP");
        request.setEmail("teste@email.com");
        request.setTelefone("11999999999");
        request.setDataRegistroClube(LocalDate.of(2020, 1, 1));

        MultipartFile file = new MockMultipartFile(
                "file", "logo.jpg", "image/jpeg", "imageContent".getBytes(StandardCharsets.UTF_8));
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());

        MotoClubeGeral entityMock = MotoClubeGeral.builder()
                .id(1L)
                .nome("Clube Teste")
                .imagemLogoBase64(base64)
                .build();

        MotoClubeGeralResponseDTO responseDTO = new MotoClubeGeralResponseDTO(
                1L, "Clube Teste", "12345678901234", "Rua A", "teste@email.com", base64, "2020-01-01");

        when(mapper.toEntity(any(MotoClubeGeralRequestDTO.class))).thenReturn(entityMock);
        when(repositiory.save(any(MotoClubeGeral.class))).thenReturn(entityMock);
        when(mapper.toResponseDTO(any(MotoClubeGeral.class))).thenReturn(responseDTO);

        MotoClubeGeralResponseDTO result = service.create(request, file);

        assertNotNull(result);
        assertEquals("Clube Teste", result.nome());
        assertEquals("12345678901234", result.cnpj());
        assertEquals("teste@email.com", result.email());
        assertEquals(base64, result.imagemLogoBase64());
        verify(repositiory).save(any(MotoClubeGeral.class));
        verify(mapper).toEntity(request);

    }

    @Test
    void shouldReturnMotoClubeById() {
        Long id = 1L;
        MotoClubeGeral entity = MotoClubeGeral.builder()
                .id(id)
                .nome("Clube Teste")
                .email("teste@email.com")
                .build();

        MotoClubeGeralResponseDTO responseDTO = new MotoClubeGeralResponseDTO(
                id, "Clube Teste", null, null, "teste@email.com", null, null);

        when(repositiory.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponseDTO(entity)).thenReturn(responseDTO);

        MotoClubeGeralResponseDTO result = service.findById(id);

        assertNotNull(result);
        assertEquals(id, result.id());
        assertEquals("Clube Teste", result.nome());
        verify(repositiory).findById(id);
        verify(mapper).toResponseDTO(entity);
    }

    @Test
    void shouldThrowExceptionWhenMotoClubeNotFound() {
        Long id = 99L;
        when(repositiory.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.findById(id));

        assertEquals("Moto clube com ID 99 não encontrado!", exception.getMessage());
        verify(repositiory).findById(id);
    }

    @Test
    void shouldReturnPaginatedMotoClubes() {

        Pageable pageable = PageRequest.of(0, 10);
        MotoClubeGeral entity = MotoClubeGeral.builder()
                .id(1L)
                .nome("Clube Teste")
                .email("teste@email.com")
                .build();

        MotoClubeGeralResponseDTO response = new MotoClubeGeralResponseDTO(
                1L, "Clube Teste", null, null, "teste@email.com", null, null);

        Page<MotoClubeGeral> entityPage = new PageImpl<>(List.of(entity));

        when(repositiory.findAll(pageable)).thenReturn(entityPage);
        when(mapper.toResponseDTO(entity)).thenReturn(response);

        Page<MotoClubeGeralResponseDTO> result = service.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Clube Teste", result.getContent().get(0).nome());
        verify(repositiory).findAll(pageable);
        verify(mapper).toResponseDTO(entity);
    }

    @Test
    void shouldUpdateMotoClubeSuccessfully() throws IOException {

        Long id = 1L;

        MotoClubeGeralRequestDTO request = new MotoClubeGeralRequestDTO();
        request.setNome("Nome Atualizado");
        request.setCnpj("98765432100000");
        request.setEnderecoSede("Rua B");
        request.setCidade("Rio de Janeiro");
        request.setUf("RJ");
        request.setEmail("atualizado@email.com");
        request.setTelefone("21999999999");
        request.setDataRegistroClube(LocalDate.of(2022, 2, 2));

        MultipartFile file = new MockMultipartFile(
                "file", "logo.jpg", "image/jpep", "novaImagem".getBytes(StandardCharsets.UTF_8));
        String base64 = Base64.getEncoder().encodeToString(file.getBytes());

        MotoClubeGeral existingEntity = MotoClubeGeral.builder()
                .id(id)
                .nome("Clube Antigo")
                .imagemLogoBase64("imagemAntiga")
                .build();

        MotoClubeGeral updatedEntity = MotoClubeGeral.builder()
                .id(id)
                .nome("Nome Atualizado")
                .imagemLogoBase64(base64)
                .build();

        MotoClubeGeralResponseDTO responseDTO = new MotoClubeGeralResponseDTO(
                id, "Nome Atualizado", "98765432100000", "Rua B", "atualizado@email.com", base64, "2022-02-02");

        when(repositiory.findById(id)).thenReturn(Optional.of(existingEntity));
        when(repositiory.save(any(MotoClubeGeral.class))).thenReturn(updatedEntity);
        when(mapper.toResponseDTO(any(MotoClubeGeral.class))).thenReturn(responseDTO);

        MotoClubeGeralResponseDTO result = service.update(id, request, file);

        assertNotNull(result);
        assertEquals("Nome Atualizado", result.nome());
        verify(repositiory).findById(id);
        verify(mapper).updateFromDto(request, existingEntity);
        verify(repositiory).save(existingEntity);
        verify(mapper).toResponseDTO(existingEntity);
    }

    @Test
    void  shouldDeleteMotoClubeSuccessfully(){

        Long id = 1L;
        MotoClubeGeral existingEntity = MotoClubeGeral.builder()
                .id(id)
                .nome("Clube Teste")
                .build();

        when(repositiory.findById(id)).thenReturn(Optional.of(existingEntity));

        service.delete(id);

        verify(repositiory).findById(id);
        verify(repositiory).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenMotoClubeNotFoundOnDelete() {

        Long id = 1L;
        when(repositiory.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> service.delete(id));

        assertEquals("Moto clube com ID 1 não encontrado!", exception.getMessage());
        verify(repositiory).findById(id);
    }
}
