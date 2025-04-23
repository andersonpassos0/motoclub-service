package com.motoclub.motoclub_service.domain.model;

import com.motoclub.motoclub_service.domain.enums.StatusMembroEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "membro")
public class Membro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;
    @NotBlank
    private String cpf;

    @NotNull
    @Column(name = "dt_nascimento")
    private LocalDate dataNascimento;

    @NotBlank
    @Column(name = "tipo_sanguineo")
    private String tipoSanguineo;

    @NotBlank
    @Column(name = "nome_contato_emergencia")
    private String contatoEmergenciaNome;

    @NotBlank
    @Column(name = "tel_contato_emergencia")
    private String contatoEmergenciaTelefone;

    @NotNull
    @Column(name = "dt_entrada_clube")
    private LocalDate dataEntradaClube;

    @Enumerated(EnumType.STRING)
    private StatusMembroEnum status;

    @Column(name = "is_piloto")
    private boolean isPiloto;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "capitulo_id")
    private Capitulo capitulo;

    @OneToMany(mappedBy = "membro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Veiculo> veiculos;

    @ManyToMany
    @JoinTable(name = "membro_cargo",
    joinColumns = @JoinColumn(name = "membro_id"),
    inverseJoinColumns = @JoinColumn(name = "cargo_id"))
    private List<Cargo> cargos;

}
