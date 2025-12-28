package com.utn.colaboradores.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ColaboradorExistenteException.class)
    public ResponseEntity<String> colaboradorExistente(ColaboradorExistenteException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(ColaboradorNoEncontradoException.class)
    public ResponseEntity<String> colaboradorNoEncontrado(ColaboradorNoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(DineroInsuficienteException.class)
    public ResponseEntity<String> dineroInsuficiente(DineroInsuficienteException ex){
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ex.getMessage());
    }

    @ExceptionHandler(FormaDeColaborarInvalidaException.class)
    public ResponseEntity<String> formaDeColaborarInvalida(FormaDeColaborarInvalidaException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
