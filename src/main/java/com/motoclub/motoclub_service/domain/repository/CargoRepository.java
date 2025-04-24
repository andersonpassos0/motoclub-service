package com.motoclub.motoclub_service.domain.repository;

import com.motoclub.motoclub_service.domain.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {
}
