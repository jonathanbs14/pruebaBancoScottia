package com.prueba.pruebascotia.service.impl;

import com.prueba.pruebascotia.bean.AlumnoDTO;
import com.prueba.pruebascotia.entity.AlumnoEntity;
import com.prueba.pruebascotia.excepcion.IdDuplicadoException;
import com.prueba.pruebascotia.mapper.AlumnoMapper;
import com.prueba.pruebascotia.repository.AlumnoRepository;
import com.prueba.pruebascotia.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private final AlumnoRepository repository;
    private final AlumnoMapper mapper;


    @Override
    public Mono<Void> grabarAlumno(AlumnoDTO alumnoDTO) {
        if (alumnoDTO.getId() == null) {
            AlumnoEntity entity = mapper.toEntity(alumnoDTO);
            return repository.save(entity).then();
        }
        return repository.existsById(alumnoDTO.getId())
                .flatMap(existe -> {
                    if (existe) {
                        return Mono.error(new IdDuplicadoException(
                                "Error: El ID '" + alumnoDTO.getId() + "' ya está registrado."
                        ));
                    }
                    AlumnoEntity entity = mapper.toEntity(alumnoDTO);
                    entity.setId(null);
                    return repository.save(entity).then();
                });
    }
    @Override
    public Mono<List<AlumnoDTO>> listaAlumnosEstadoActivo() {
        return repository.findByEstado("A")
                .map(mapper::toDto)
                .collectList();
    }
}
