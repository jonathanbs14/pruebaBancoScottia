package com.prueba.pruebascotia.mapper;
import com.prueba.pruebascotia.bean.AlumnoDTO;
import com.prueba.pruebascotia.entity.AlumnoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AlumnoMapper {

    AlumnoEntity toEntity(AlumnoDTO alumnoDTO);

    AlumnoDTO toDto(AlumnoEntity alumno);
}
