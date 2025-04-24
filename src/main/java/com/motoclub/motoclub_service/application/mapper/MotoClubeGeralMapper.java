package com.motoclub.motoclub_service.application.mapper;

import com.motoclub.motoclub_service.application.dto.MotoClubeGeralRequestDTO;
import com.motoclub.motoclub_service.application.dto.MotoClubeGeralResponseDTO;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MotoClubeGeralMapper {

    MotoClubeGeralMapper INSTANCE = Mappers.getMapper(MotoClubeGeralMapper.class);

    MotoClubeGeral toEntity(MotoClubeGeralRequestDTO request);
    MotoClubeGeralResponseDTO toResponseDTO(MotoClubeGeral entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imagemLogoBase64", ignore = true)
    @Mapping(target = "dataCriacaoRegistro", ignore = true)
    void updateFromDto(MotoClubeGeralRequestDTO request, @MappingTarget MotoClubeGeral entity);
}
