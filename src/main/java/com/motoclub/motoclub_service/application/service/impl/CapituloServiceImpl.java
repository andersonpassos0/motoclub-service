package com.motoclub.motoclub_service.application.service.impl;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.application.mapper.CapituloMapper;
import com.motoclub.motoclub_service.application.service.CapituloService;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import com.motoclub.motoclub_service.domain.repository.CapituloRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CapituloServiceImpl implements CapituloService {

    private final CapituloRepository capituloRepository;
    private final CapituloMapper capituloMapper;

    @Override
    public CapituloResponseDTO create(CapituloRequestDTO request) {
        System.out.println("request -> " + request.motoClubeGeral());
        Capitulo capitulo = capituloMapper.toEntity(request);
        System.out.println("entity -> " + capitulo.getMotoClubeGeral());
        Capitulo capituloSalvo = capituloRepository.save(capitulo);
        return capituloMapper.toDto(capituloSalvo);
    }

    @Override
    public CapituloResponseDTO findById(Long id) {
        Capitulo capitulo = capituloRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Capítulo não encontrado!"));
        return capituloMapper.toDto(capitulo);
    }

    @Override
    public Page<CapituloResponseDTO> findAll(Pageable pageable) {
        return capituloRepository.findAll(pageable)
                .map(capituloMapper::toDto);
    }

    @Override
    public CapituloResponseDTO update(Long id, CapituloRequestDTO request) {
        Capitulo capitulo = capituloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Capítulo não encontrado"));

        Capitulo atualizado = capituloMapper.toEntity(request);
        atualizado.setId(capitulo.getId());
        atualizado.setDataCriacaoCapitulo(capitulo.getDataCriacaoCapitulo());

        Capitulo salvo = capituloRepository.save(atualizado);

        return capituloMapper.toDto(salvo);
    }

    @Override
    public void delete(Long id) {
        Capitulo capitulo = capituloRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Capítulo não encontrado"));
        capituloRepository.delete(capitulo);
    }
}
