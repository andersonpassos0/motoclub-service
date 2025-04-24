package com.motoclub.motoclub_service.domain.repository;

import com.motoclub.motoclub_service.domain.model.MotoClubeGeral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotoClubeGeralRepository extends JpaRepository<MotoClubeGeral, Long> {
}
