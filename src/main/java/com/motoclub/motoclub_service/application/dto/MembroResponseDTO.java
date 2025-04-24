package com.motoclub.motoclub_service.application.dto;

import com.motoclub.motoclub_service.domain.enums.StatusMembroEnum;
import com.motoclub.motoclub_service.domain.model.Veiculo;

import java.time.LocalDate;
import java.util.List;

public record MembroResponseDTO (
        Long id,
        String nome,
        String cpf,
        String telefone,
        String email,
        String numeroCarteiraHabilitacao,
        String tipoCarteiraHabilitacao,
        LocalDate validadeCarteiraHabilitacao,
        LocalDate dataNascimento,
        String tipoSanguineo,
        String contatoEmergenciaNome,
        String contatoEmergenciaTelefone,
        LocalDate dataEntradaClube,
        StatusMembroEnum status,
        boolean piloto,
        String capituloNome,
        List<VeiculoResponseDTO> veiculos,
        List<String> cargosNome
) {}
