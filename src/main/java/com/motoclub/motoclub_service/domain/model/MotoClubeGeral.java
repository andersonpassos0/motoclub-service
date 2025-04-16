package com.motoclub.motoclub_service.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "moto_clube_geral")
public class MotoClubeGeral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @Column(name = "data_registro", nullable = false)
    private LocalDate dataRegistroClube;

    @Column
    private String cnpj;

    @NotBlank(message = "O endereço é obrigatório")
    @Column(nullable = false)
    private String enderecoSede;

    @NotBlank(message = "A cidade é obrigatória")
    @Column(nullable = false)
    private String cidade;

    @NotBlank(message = "O Estado é obrigatório")
    @Column(nullable = false)
    private String uf;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O e-mail é obrigatório")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "O telefone é obrigatório")
    @Column(nullable = false)
    private String telefone;

    @Lob
    @Column(columnDefinition = "CLOB")
    private String logomarcaBase64;

    @PastOrPresent
    @Column(name = "data_criacao_registro", nullable = false, updatable = false)
    private LocalDateTime dataCriacaoRegistro;

    @PrePersist
    protected void onCreate() {
        this.dataCriacaoRegistro = LocalDateTime.now();
    }


}
