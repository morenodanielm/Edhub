package com.edhub.services;

import java.util.Collections;
import java.util.List;
import com.edhub.exceptions.EdhubExceptions;
import com.edhub.mapper.MensajeDTOToMensaje;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.edhub.models.Mensaje;
import com.edhub.repositories.MensajeRepository;
import com.edhub.services.dto.MensajeDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
//clase de servicio para entidad mensaje
public class MensajeService {

    private final MensajeRepository mensajeRepository;
    private final MensajeDTOToMensaje mapper;

    public Mensaje agregarMensaje(MensajeDTO mensajeDTO)  {
        Mensaje mensaje = mapper.map(mensajeDTO);
        return mensajeRepository.save(mensaje);    
    }

    public Mensaje obtenerMensaje(Long id) {
        return mensajeRepository.findById(id)
            .orElseThrow(() -> new EdhubExceptions("Mensaje no hallado", HttpStatus.NOT_FOUND));
    }

    // retornará todos los mensajes del más antiguo al más reciente
    public List<Mensaje> obtenerTodos() {
    	List<Mensaje> mensajes = mensajeRepository.findAll();
    	Collections.sort(mensajes, Collections.reverseOrder());
        return mensajes;
    }

    // eliminará un mensaje en específico
    public void eliminarMensaje(Mensaje mensaje) {
        if(!mensajeRepository.existsById(mensaje.getIdMensaje())) {
            throw new EdhubExceptions("El mensaje no existe", HttpStatus.NOT_FOUND);
        }
        mensajeRepository.delete(mensaje);
    }

}
