package com.motoclub.motoclub_service.application.service.impl;

import com.motoclub.motoclub_service.application.dto.MembroRequestDTO;
import com.motoclub.motoclub_service.application.dto.MembroResponseDTO;
import com.motoclub.motoclub_service.application.mapper.MembroMapper;
import com.motoclub.motoclub_service.application.service.MembroService;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import com.motoclub.motoclub_service.domain.model.Cargo;
import com.motoclub.motoclub_service.domain.model.Membro;
import com.motoclub.motoclub_service.domain.repository.CapituloRepository;
import com.motoclub.motoclub_service.domain.repository.CargoRepository;
import com.motoclub.motoclub_service.domain.repository.MembroRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembroServiceImpl implements MembroService {

    private final MembroRepository membroRepository;
    private final CapituloRepository capituloRepository;
    private final CargoRepository cargoRepository;
    private final MembroMapper membroMapper;


    @Override
    public MembroResponseDTO create(MembroRequestDTO request) {
        Capitulo capitulo = capituloRepository.findById(request.capituloId())
                .orElseThrow(() -> new EntityNotFoundException("Capítulo não encontrado"));

        List<Cargo> cargos = cargoRepository.findAllById(request.cargosId());
        if(cargos.isEmpty()){
            throw new EntityNotFoundException("Nenhum cargo encontrado para os IDs fornecidos");
        }

        Membro membro = membroMapper.toEntity(request);
        membro.setCapitulo(capitulo);
        membro.setCargos(cargos);

        Membro membroSalvo = membroRepository.save(membro);

        return membroMapper.toDto(membroSalvo);
    }

    @Override
    public MembroResponseDTO findById(Long id) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado com ID: " + id));
        return membroMapper.toDto(membro);
    }

    @Override
    public Page<MembroResponseDTO> findAll(Pageable pageable) {
        return membroRepository.findAll(pageable)
                .map(membroMapper::toDto);
    }

    @Override
    public MembroResponseDTO update(Long id, MembroRequestDTO request) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado com id: " + id));

        membroMapper.updateFromDto(request, membro);
//        membro.setDataEntradaClube(membro.getDataEntradaClube());
        Membro membroAtualizado = membroRepository.save(membro);
        return membroMapper.toDto(membroAtualizado);
    }

    @Override
    public void delete(Long id) {
        Membro membro = membroRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membro não encontrado com ID: " + id));
        try {
            membroRepository.delete(membro);
        } catch (DataIntegrityViolationException e){
            throw new IllegalStateException("Não é possível excluir o membro. Existe relacionamentos ativos");
        }
    }
}
