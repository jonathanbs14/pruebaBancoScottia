package com.prueba.pruebascotia.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.CONFLICT)
public class IdDuplicadoException extends RuntimeException {
    public IdDuplicadoException(String message) {
        super(message);
    }
}
