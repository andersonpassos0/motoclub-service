package com.motoclub.motoclub_service.infrastructure.config;

import com.motoclub.motoclub_service.domain.enums.DiaSemana;
import com.motoclub.motoclub_service.domain.model.*;
import com.motoclub.motoclub_service.domain.repository.CapituloRepository;
import com.motoclub.motoclub_service.domain.repository.CargoRepository;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import com.motoclub.motoclub_service.domain.repository.VeiculoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabases(MotoClubeGeralRepository motoClubeGeralRepository,
                                    CapituloRepository capituloRepository,
                                    CargoRepository cargoRepository,
                                    VeiculoRepository veiculoRepository) {

        return args -> {
            MotoClubeGeral motoClubeGeral;

            if (motoClubeGeralRepository.findAll().isEmpty()) {
                motoClubeGeral = motoClubeGeralRepository.save(
                        new MotoClubeGeral(null, "AMM Brasil", LocalDate.parse("2020-01-01"),
                                "01.234.567/0001-00", "Rua ABC, 110 - Centro", "São Paulo", "SP",
                                "sede@ammbrasil.org", "11999999999", "base64", LocalDateTime.now())
                );
            } else {
                motoClubeGeral = motoClubeGeralRepository.findAll().get(0);
            }

            if (capituloRepository.findAll().isEmpty()) {
                capituloRepository.save(new Capitulo(null, "AMM - Eunapolis", "Rua A, 123", "Eunapolis", "BA", "45820-000", "capitulo.eunapolis@amm.com.br", DiaSemana.QUINTA_FEIRA, LocalDate.parse("2020-01-01").toString(), motoClubeGeral));
                capituloRepository.save(new Capitulo(null, "AMM - Porto Seguro", "Rua B, 123", "Porto Seguro", "BA", "45810-000", "capitulo.portoseguro@amm.com.br", DiaSemana.QUINTA_FEIRA, LocalDate.parse("2020-01-01").toString(), motoClubeGeral));
            }

            if (cargoRepository.findAll().isEmpty()) {
                cargoRepository.save(new Cargo(null, "Diretor Nacional"));
                cargoRepository.save(new Cargo(null, "Diretor de Capitulo"));
                cargoRepository.save(new Cargo(null, "Capelão"));
                cargoRepository.save(new Cargo(null, "Secretário"));
                cargoRepository.save(new Cargo(null, "Tesoureiro"));
                cargoRepository.save(new Cargo(null, "Captão de Estrada"));
                cargoRepository.save(new Cargo(null, "Membro"));
            }

//            if (veiculoRepository.findAll().isEmpty()){
//                veiculoRepository.save(new Veiculo(null, "123456789", "ABC1D23", "Yamaha", "Virago 535", 2000, "Preta", membro));
//            }
        };
    }
}
