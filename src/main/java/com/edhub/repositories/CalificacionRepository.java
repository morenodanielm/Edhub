package com.edhub.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.edhub.models.Calificacion;
import com.edhub.models.Usuario;

public interface CalificacionRepository extends JpaRepository<Calificacion, Long>{

    Optional<Calificacion> findByCalificado(Usuario calificado);

    Optional<Calificacion> findByCalificador(Usuario calificador);

    List<Calificacion> findAllByCalificado(Usuario calificado);
    
    List<Calificacion> findAllByCalificador(Usuario calificador);
    
}
