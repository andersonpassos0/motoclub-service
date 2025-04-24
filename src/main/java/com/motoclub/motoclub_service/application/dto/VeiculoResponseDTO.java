package com.motoclub.motoclub_service.application.dto;

public record VeiculoResponseDTO(
        Long id,
        String renavam,
        String placa,
        String marca,
        String modelo,
        int ano,
        String cor
) {}
