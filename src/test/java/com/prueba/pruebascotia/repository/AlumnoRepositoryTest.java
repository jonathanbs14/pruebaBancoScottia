package com.prueba.pruebascotia.repository;


import com.prueba.pruebascotia.entity.AlumnoEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@DataR2dbcTest
class AlumnoRepositoryTest {

    @Autowired
    private AlumnoRepository repository;

    @Test
    void findByEstado_shouldReturnActiveAlumnos() {
        AlumnoEntity activo = new AlumnoEntity(1, "Ana", "Suarez", "activo", 20);
        AlumnoEntity inactivo = new AlumnoEntity(2, "Juan", "Perez", "inactivo", 22);

        repository.saveAll(Flux.just(activo, inactivo)).blockLast();

        Flux<AlumnoEntity> activosFlux = repository.findByEstado("activo");

        StepVerifier.create(activosFlux)
                .expectNextMatches(alumno -> alumno.getEstado().equals("activo"))
                .verifyComplete();
    }
}
