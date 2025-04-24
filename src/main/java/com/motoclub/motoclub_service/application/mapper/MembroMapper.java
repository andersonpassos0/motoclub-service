package com.motoclub.motoclub_service.application.mapper;

import com.motoclub.motoclub_service.application.dto.MembroRequestDTO;
import com.motoclub.motoclub_service.application.dto.MembroResponseDTO;
import com.motoclub.motoclub_service.domain.model.Membro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "Spring")
public interface MembroMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "capitulo", ignore = true)
    @Mapping(target = "cargos", ignore = true)
    @Mapping(target = "veiculos", ignore = true)
    Membro toEntity(MembroRequestDTO request);

    @Mapping(source = "capitulo.nomeCapitulo", target = "capituloNome")
    @Mapping(target = "cargosNome", expression = "java(membro.getCargos().stream().map(c -> c.getNome()).toList())")
    MembroResponseDTO toDto(Membro membro);

    @Mapping(target = "id", ignore = true)
    void updateFromDto(MembroRequestDTO request, @MappingTarget Membro entity);
}
