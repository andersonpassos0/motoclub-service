package com.motoclub.motoclub_service.domain.repository;

import com.motoclub.motoclub_service.domain.model.Capitulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapituloRepository extends JpaRepository<Capitulo, Long> {
}
