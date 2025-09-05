package com.prueba.pruebascotia.bean;

import lombok.*;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDTO {

    private Integer  id;

    private String nombre;

    private String apellido;

    private String estado;

    private int edad;


}
