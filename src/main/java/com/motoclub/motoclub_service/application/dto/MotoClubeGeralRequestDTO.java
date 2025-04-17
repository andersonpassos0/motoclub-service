package com.motoclub.motoclub_service.application.dto;

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

    private String nome;
    private String cnpj;
    private String enderecoSede;
    private String cidade;
    private String uf;
    private String email;
    private String telefone;
    private String imagemLogoBase64;
    private LocalDate dataRegistroClube;
}
