package com.springboot.controllers;

import com.springboot.models.entitys.Reserva;
import com.springboot.models.repository.EspecialidadRepository;
import com.springboot.models.repository.MedicoRepository;
import com.springboot.models.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Controller
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private MedicoRepository medicoRepository;
    

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("reserva", new Reserva());
        model.addAttribute("especialidades", especialidadRepository.findAll());
        model.addAttribute("medicos", medicoRepository.findAll());
        return "registro";
    }

    
    @PostMapping("/reservas")
    public String registrarReserva(@ModelAttribute Reserva reserva, RedirectAttributes redirectAttributes) {
        try {
        	 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
             String fechaActual = LocalDateTime.now().format(formatter);
             reserva.setFechaInscripcion(fechaActual);
            reservaRepository.save(reserva);
            redirectAttributes.addFlashAttribute("success", "Reserva registrada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la reserva.");
        }
        return "redirect:/registro"; 
    }
    

    @GetMapping("/listado")
    public ResponseEntity<List<Reserva>> listarReservas() {
        try {
            List<Reserva> reservas = reservaRepository.findAll(); // Obtiene todas las reservas
            if (reservas.isEmpty()) {
                return ResponseEntity.noContent().build(); // Devuelve 204 No Content si no hay reservas
            }
            return ResponseEntity.ok(reservas); // Retorna la lista de reservas como JSON
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Devuelve 500 Internal Server Error
        }
    }

    @GetMapping("/reservas/actualizar/{id}")
    public String mostrarFormularioActualizar(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        
        if (reserva.isPresent()) {
            model.addAttribute("reserva", reserva.get());
            model.addAttribute("especialidades", especialidadRepository.findAll());
            model.addAttribute("medicos", medicoRepository.findAll());
            return "actualizar"; 
        } else {
            redirectAttributes.addFlashAttribute("error", "Reserva no encontrada.");
            return "redirect:/listado"; 
        }
    }

    @PutMapping("/reservas/actualizar/{id}")
    public ResponseEntity<String> actualizarReserva(
            @PathVariable Integer id, 
            @RequestBody Reserva reservaActualizada) {
        
        // Busca la reserva existente
        Optional<Reserva> reservaExistente = reservaRepository.findById(id);
        
        if (reservaExistente.isPresent() && isValid(reservaActualizada)) {
            // Actualiza los campos de la reserva existente
            Reserva reserva = reservaExistente.get();
            reserva.setDni(reservaActualizada.getDni());
            reserva.setEmail(reservaActualizada.getEmail());
            reserva.setTelefono(reservaActualizada.getTelefono());
            reserva.setFechaCita(reservaActualizada.getFechaCita());
            reserva.setHoraCita(reservaActualizada.getHoraCita());
            reserva.setNombrePaciente(reservaActualizada.getNombrePaciente());
            
            // Guarda la reserva actualizada
            reservaRepository.save(reserva);
            
            // Retorna respuesta exitosa
            return ResponseEntity.ok("Reserva actualizada con éxito.");
        } else {
            // Retorna error si la reserva no fue encontrada o si los datos son inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Reserva no encontrada o datos inválidos.");
        }
    }


    // Método para validar los campos de la reserva
    private boolean isValid(Reserva reserva) {
        return reserva.getDni() != null && !reserva.getDni().isEmpty()
            && reserva.getEmail() != null && !reserva.getEmail().isEmpty()
            && reserva.getTelefono() != null && !reserva.getTelefono().isEmpty()
            && reserva.getFechaCita() != null
            && reserva.getHoraCita() != null
            && reserva.getNombrePaciente() != null && !reserva.getNombrePaciente().isEmpty();
    }

    

        // NUEVO MÉTODO PARA ELIMINAR RESERVAS
        @GetMapping("/reservas/eliminar/{id}")
        public String eliminarReserva(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
            try {
                Optional<Reserva> reserva = reservaRepository.findById(id);
                if (reserva.isPresent()) {
                    reservaRepository.delete(reserva.get());
                    redirectAttributes.addFlashAttribute("success", "Reserva eliminada con éxito.");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Reserva no encontrada.");
                }
            } catch (Exception e) {
                redirectAttributes.addFlashAttribute("error", "Error al eliminar la reserva.");
            }
            return "redirect:/listado";
        }
        
            @Configuration
            public class WebConfig implements WebMvcConfigurer {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
                }
            }
        
    
    }

