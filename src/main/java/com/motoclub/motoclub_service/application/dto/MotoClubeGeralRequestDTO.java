package com.motoclub.motoclub_service.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MotoClubeGeralRequestDTO {

    @NotNull
    private String nome;
    private String cnpj;
    @NotNull
    private String enderecoSede;
    @NotNull
    private String cidade;
    @NotNull
    private String uf;
    @NotNull
    private String email;
    private String telefone;
    private String imagemLogoBase64;
    private LocalDate dataRegistroClube;
}
