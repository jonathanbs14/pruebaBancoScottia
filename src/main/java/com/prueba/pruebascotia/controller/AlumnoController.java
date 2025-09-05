package com.prueba.pruebascotia.controller;

import com.prueba.pruebascotia.bean.AlumnoDTO;
import com.prueba.pruebascotia.service.AlumnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alumno")
public class AlumnoController {

    private final AlumnoService service;


    public AlumnoController(AlumnoService service) {
        this.service = service;
    }

    @PostMapping("/insertar")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<Void>> insertarAlumno(@RequestBody AlumnoDTO in) {
        return service.grabarAlumno(in)
                .then(Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @GetMapping("/listar")
    public Mono<ResponseEntity<List<AlumnoDTO>>> listarAlumnosEstadoActivo() {
        return service.listaAlumnosEstadoActivo()
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.internalServerError().build()
                ));
    }
}



