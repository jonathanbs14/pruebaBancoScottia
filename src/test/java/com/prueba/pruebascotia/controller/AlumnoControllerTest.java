package com.prueba.pruebascotia.controller;

import com.prueba.pruebascotia.bean.AlumnoDTO;
import com.prueba.pruebascotia.entity.AlumnoEntity;
import com.prueba.pruebascotia.mapper.AlumnoMapper;
import com.prueba.pruebascotia.service.AlumnoService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Import(AlumnoControllerTest.TestConfig.class)
@WebFluxTest(controllers = AlumnoController.class)
class AlumnoControllerTest {

    private final WebTestClient webTestClient;
    private final AlumnoService alumnoService;


    @Autowired
    public AlumnoControllerTest(WebTestClient webTestClient, AlumnoService alumnoService) {
        this.webTestClient = webTestClient;
        this.alumnoService = alumnoService;

    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {
        @Bean
        public AlumnoService alumnoService() {
            return Mockito.mock(AlumnoService.class);
        }

        @Bean
        public AlumnoMapper alumnoMapper() {
            return Mockito.mock(AlumnoMapper.class);
        }
    }

    @Test
    void insertarAlumno_shouldReturnCreatedAndAlumnoDto() {
        AlumnoDTO requestDto = new AlumnoDTO(1, "Ana", "tores", "A", 15);
        AlumnoEntity savedEntity = new AlumnoEntity(1, "Ana", "tores", "A", 15);

        when(alumnoService.grabarAlumno(any(AlumnoDTO.class)))
                .thenReturn(Mono.just(savedEntity).then());



        webTestClient.post() // Petición POST
                .uri("/alumno/insertar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody().isEmpty();

        verify(alumnoService, times(1)).grabarAlumno(any(AlumnoDTO.class));
    }

    @Test
    void listarAlumnosEstadoActivo_whenAlumnosExist_shouldReturnOkAndListOfDtos() {
        AlumnoDTO dto1 = new AlumnoDTO(1, "Ana", "tores", "A", 15);
        AlumnoDTO dto2 = new AlumnoDTO(2, "Luis", "diaz", "A", 24);

        List<AlumnoDTO> expectedList = List.of(dto1, dto2);

        when(alumnoService.listaAlumnosEstadoActivo()).thenReturn(Mono.just(expectedList));

        webTestClient.get()
                .uri("/alumno/listar")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AlumnoDTO.class)
                .hasSize(2)
                .isEqualTo(expectedList);
    }

    @Test
    void listarAlumnosEstadoActivo_whenServiceFails_shouldReturnInternalServerError() {

        when(alumnoService.listaAlumnosEstadoActivo()).thenReturn(Mono.error(new RuntimeException("Internal Service Error")));

        webTestClient.get()
                .uri("/alumno/listar")
                .exchange()
                .expectStatus().is5xxServerError();
    }
}
