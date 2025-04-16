package com.motoclub.motoclub_service.application.dto;

import java.time.LocalDate;

public record MotoClubeGeralRequestDTO (
   String nome,
   String cnpj,
   String enderecoSede,
   String cidade,
   String uf,
   String email,
   String telefone,
   String imagemLogoBase64,
   LocalDate dataRegistroClube
){}
