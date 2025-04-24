package com.motoclub.motoclub_service.application.mapper;

import com.motoclub.motoclub_service.application.dto.CapituloRequestDTO;
import com.motoclub.motoclub_service.application.dto.CapituloResponseDTO;
import com.motoclub.motoclub_service.domain.model.Capitulo;
import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CapituloMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "motoClubeGeral", source = "motoClubeGeral", qualifiedByName = "mapIdToMotoClubeGeral")
    Capitulo toEntity(CapituloRequestDTO request);

    @Mapping(target = "motoClubeGeral", source = "motoClubeGeral", qualifiedByName = "mapMotoClubeGeralToId")
    CapituloResponseDTO toDto(Capitulo capitulo);

    @Named("mapIdToMotoClubeGeral")
    default MotoClubeGeral map(Long id){
        if (id == null) return null;
        MotoClubeGeral motoClubeGeral = new MotoClubeGeral();
        motoClubeGeral.setId(id);
        return motoClubeGeral;
    }

    @Named("mapMotoClubeGeralToId")
    default Long map(MotoClubeGeral motoClubeGeral) {
        return motoClubeGeral != null ? motoClubeGeral.getId() : null;
    }

}
