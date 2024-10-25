package com.springboot.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.models.entitys.Especialidad;
import com.springboot.models.repository.EspecialidadRepository;

import java.util.List;

@Service
public class EspecialidadServiceImpl {

    @Autowired
    private EspecialidadRepository especialidadRepository;


    public List<Especialidad> listar() {
        return especialidadRepository.findAll();
    }
}
