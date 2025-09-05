package com.prueba.pruebascotia.repository;

import com.prueba.pruebascotia.entity.AlumnoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AlumnoRepository extends ReactiveCrudRepository<AlumnoEntity, Integer> {
    Flux<AlumnoEntity> findByEstado(String estado);
}
