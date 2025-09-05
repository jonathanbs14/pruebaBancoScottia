package com.prueba.pruebascotia.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoEntity {
    @Id
    private Integer id;
    private String nombre;
    private String apellido;
    private String estado;
    private Integer edad;
}
