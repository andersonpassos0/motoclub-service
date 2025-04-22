package com.motoclub.motoclub_service.application.dto;

import com.motoclub.motoclub_service.domain.enums.DiaSemana;

public record CapituloResponseDTO(
        Long id,
        String nomeCapitulo,
        String enderecoSede,
        String cidade,
        String uf,
        String cep,
        String email,
        DiaSemana diaReuniao,
        String dataCriacaoCapitulo,
        Long motoClubeGeral
) {
}
