package com.motoclub.motoclub_service.domain.model;

import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Capitulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nomeCapitulo;

    @Column (name = "endereco_sede")
    private String enderecoSede;
    private String cidade;
    private String uf;
    private String cep;

    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "dia_reuniao")
    private DiaSemana diaReuniao;

    @Column(name = "dt_criacao_capitulo")
    private String dataCriacaoCapitulo;

    @ManyToOne
    @JoinColumn(name = "motoclube_geral_id", nullable = false)
    private MotoClubeGeral motoClubeGeral;
}
