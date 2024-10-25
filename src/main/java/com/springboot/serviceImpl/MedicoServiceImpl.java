package com.springboot.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.models.entitys.Medico;
import com.springboot.models.repository.MedicoRepository;

import java.util.List;

@Service
public class MedicoServiceImpl {

    @Autowired
    private MedicoRepository medicoRepository;

    
    public List<Medico> listar() {
        return medicoRepository.findAll();
    }
}
