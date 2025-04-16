package com.motoclub.motoclub_service.application.dto;

public record MotoClubeGeralResponseDTO (
        Long id,
        String nome,
        String cnpj,
        String enderecoSede,
        String email,
        String imagemLogoBase64,
        String dataRegistroClube
) {}
