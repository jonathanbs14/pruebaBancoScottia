package com.prueba.pruebascotia.service.impl;

import com.prueba.pruebascotia.bean.AlumnoDTO;
import com.prueba.pruebascotia.entity.AlumnoEntity;
import com.prueba.pruebascotia.excepcion.IdDuplicadoException;
import com.prueba.pruebascotia.mapper.AlumnoMapper;
import com.prueba.pruebascotia.repository.AlumnoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository repository;

    @Mock
    private AlumnoMapper mapper;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Test
    void grabarAlumno_whenIdIsNew_shouldSaveAndReturnAlumno() {
        AlumnoDTO newAlumnoDto = new AlumnoDTO(100, "Nuevo Alumno", "nuevo@test.com", "A", 20);
        AlumnoEntity newAlumnoEntity = new AlumnoEntity(100, "Nuevo Alumno", "nuevo@test.com", "A", 20);

        when(mapper.toEntity(newAlumnoDto)).thenReturn(newAlumnoEntity);
        when(repository.existsById(100)).thenReturn(Mono.just(false)); // Simulate ID does not exist
        when(repository.save(newAlumnoEntity)).thenReturn(Mono.just(newAlumnoEntity)); // Simulate save operation

        Mono<Void> resultMono = alumnoService.grabarAlumno(newAlumnoDto);

        StepVerifier.create(resultMono)
                .verifyComplete();


        verify(repository).existsById(100);
        verify(repository).save(newAlumnoEntity);
    }

    @Test
    void grabarAlumno_whenIdAlreadyExists_shouldThrowIdDuplicadoException() {
        AlumnoDTO duplicateAlumnoDto = new AlumnoDTO(1, "Alumno Duplicado", "duplicado@test.com", "A", 20);

        when(repository.existsById(1)).thenReturn(Mono.just(true));


        Mono<Void> resultMono = alumnoService.grabarAlumno(duplicateAlumnoDto);

        StepVerifier.create(resultMono)
                .expectError(IdDuplicadoException.class)
                .verify();

        verify(repository, never()).save(any(AlumnoEntity.class));
    }

    @Test
    void listaAlumnosEstadoActivo_whenAlumnosExist_shouldReturnListOfDtos() {
        AlumnoEntity entity1 = new AlumnoEntity(1, "Ana", "tores", "A", 15);
        AlumnoEntity entity2 = new AlumnoEntity(2, "Luis", "diaz", "A", 24);


        when(repository.findByEstado("A")).thenReturn(Flux.just(entity1, entity2));

        when(mapper.toDto(any(AlumnoEntity.class))).thenAnswer(invocation -> {
            AlumnoEntity entity = invocation.getArgument(0);
            return new AlumnoDTO(entity.getId(), entity.getNombre(), entity.getApellido(), entity.getEstado(), entity.getEdad());
        });
        Mono<List<AlumnoDTO>> resultMono = alumnoService.listaAlumnosEstadoActivo();


        StepVerifier.create(resultMono)
                .expectNextMatches(list ->
                        list.size() == 2 &&
                                list.get(0).getNombre().equals("Ana") &&
                                list.get(1).getNombre().equals("Luis")
                )
                .verifyComplete();

    }

    @Test
    void listaAlumnosEstadoActivo_whenNoAlumnosExist_shouldReturnEmptyList() {

        when(repository.findByEstado("A")).thenReturn(Flux.empty()); // Simulate finding no students

        Mono<List<AlumnoDTO>> resultMono = alumnoService.listaAlumnosEstadoActivo();

        StepVerifier.create(resultMono)
                .expectNextMatches(List::isEmpty)
                .verifyComplete();
    }
}
