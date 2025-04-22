package com.motoclub.motoclub_service.application.dto;

import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CapituloRequestDTO (

        @NotBlank
        String nomeCapitulo,
        @NotNull
        String enderecoSede,
        @NotNull
        String cidade,
        @NotNull
        String uf,
        @NotNull
        String cep,
        @NotNull
        String email,
        DiaSemana diaReuniao,
        String dataCriacaoCapitulo,
        Long motoClubeGeral
) {}
