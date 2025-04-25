package com.motoclub.motoclub_service.application.dto;

import com.motoclub.motoclub_service.domain.enums.StatusMembroEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MembroRequestDTO (
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        @NotBlank(message = "O Numero de telefone é obrigatório")
        String telefone,
        @NotBlank(message = "O endereço é obrigatório")
        String endereco,
        @NotBlank(message = "O bairro é obrigatório")
        String bairro,
        @NotBlank(message = "A cidade é obrigatória")
        String cidade,
        @NotBlank(message = "O estado é obrigatório")
        String uf,
        @NotBlank(message = "O CEP é obrigatório")
        String cep,

        String email,
        String numeroCarteiraHabilitacao,
        String tipoCarteiraHabilitacao,
        LocalDate validadeCarteiraHabilitacao,

        @NotNull(message = "A data de nascimento é obrigatório")
        LocalDate dataNascimento,
        @NotBlank(message = "O tipo sanguíneo é obrigatório")
        String tipoSanguineo,
        @NotBlank(message = "O contato de emergência é obrigatório")
        String contatoEmergenciaNome,
        @NotBlank(message = "O contato de emergência é obrigatório")
        String contatoEmergenciaTelefone,
        @NotNull(message = "A data de entrada no clube é obrigatório")
        LocalDate dataEntradaClube,
        StatusMembroEnum status,
        boolean piloto,
        @NotNull(message = "O membro precisa ser filiado a um Capítulo")
        Long capituloId,
        List<Long> cargosId
){}
