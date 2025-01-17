package com.springboot.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.models.entitys.Medico;

@Repository
public interface MedicoRepository extends JpaRepository<Medico, Integer> {
}
