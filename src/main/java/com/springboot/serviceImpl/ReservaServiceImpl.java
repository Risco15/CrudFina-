package com.springboot.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.models.entitys.Reserva;
import com.springboot.models.repository.ReservaRepository;

import java.util.List;

@Service
public class ReservaServiceImpl {

    @Autowired
    private ReservaRepository reservaRepository;


    public void guardar(Reserva reserva) {
        reservaRepository.save(reserva);
    }

   
    public List<Reserva> listar() {
        return reservaRepository.findAll();
    }
}
