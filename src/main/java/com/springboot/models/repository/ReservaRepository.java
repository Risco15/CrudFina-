package com.springboot.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.models.entitys.Reserva;


@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
	 List<Reserva> findAllByOrderByFechaCitaAsc();

}
