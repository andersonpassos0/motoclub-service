package com.motoclub.motoclub_service.application.mapper;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CapituloMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motoClubeGeral", ignore = true)
    Capitulo toEntity(CapituloRequestDTO request);

    CapituloResponseDTO toDto(Capitulo capitulo);
}
