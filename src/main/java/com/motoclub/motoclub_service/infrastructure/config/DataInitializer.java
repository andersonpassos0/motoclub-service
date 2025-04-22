package com.motoclub.motoclub_service.infrastructure.config;

import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import com.motoclub.motoclub_service.domain.repository.MotoClubeGeralRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.util.Calendar.DATE;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initMotoClubeGeralDatabase(MotoClubeGeralRepository motoClubeGeralRepository){
        return args -> {
            if (motoClubeGeralRepository.findAll().isEmpty()){
                motoClubeGeralRepository.save(new MotoClubeGeral(null, "AMM Brasil",  LocalDate.parse("2020-01-01"), "01.234.567/0001-00", "Rua ABC, 110 - Centro", "São Paulo", "SP", "sede@ammbrasil.org", "11999999999", "base64", LocalDateTime.now()));

            }
        };
    }
}
