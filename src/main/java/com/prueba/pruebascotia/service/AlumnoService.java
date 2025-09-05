package com.prueba.pruebascotia.service;


import com.prueba.pruebascotia.bean.AlumnoDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AlumnoService {
    Mono<Void> grabarAlumno(AlumnoDTO alumnoDTO);
     Mono<List<AlumnoDTO>>listaAlumnosEstadoActivo();
}
