package com.motoclub.motoclub_service.application.dto;

import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CapituloRequestDTO (

        @NotBlank(message = "O nome é obrigatório")
        String nomeCapitulo,
        @NotBlank(message = "O endereço é obrigatório")
        String enderecoSede,
        @NotBlank(message = "A cidade é obrigatório")
        String cidade,
        @NotBlank(message = "O estado é obrigatório")
        String uf,
        @NotBlank(message = "O CEP é obrigatório")
        String cep,
        @NotBlank(message = "O e-mail é obrigatório")
        String email,
        DiaSemana diaReuniao,
        String dataCriacaoCapitulo,
        Long motoClubeGeral
) {}
