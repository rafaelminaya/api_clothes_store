package com.rminaya.clothes.store.clothes_store.api.controllers.error_handler;

import com.rminaya.clothes.store.clothes_store.api.models.responses.BaseErrorResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ErrorResponse;
import com.rminaya.clothes.store.clothes_store.api.models.responses.ErrorsResponse;
import com.rminaya.clothes.store.clothes_store.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {
    @ExceptionHandler(IdNotFoundException.class) // anotación que intercepta la excepción indicada
    public BaseErrorResponse handleIdNotFound(IdNotFoundException exception) {
        // Devolvemos una instancia de "ErrorResponse", con los atributos para la clase padre e hijo
        // "ErrorResponse" es hijo de la clase "BaseErrorResponse" a retornar.
        return ErrorResponse.builder()
                .error(exception.getMessage()) // asignamos el mensaje de la excepción.
                .status(HttpStatus.BAD_REQUEST.name()) // "BAD_REQUEST"
                .code(HttpStatus.BAD_REQUEST.value()) // 400
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // anotación que intercepta la excepción indicada
    public BaseErrorResponse handleArguments(MethodArgumentNotValidException exception) {
        // listado que contendrá los errores obtenidos de la excepción "MethodArgumentNotValidException"
        var errors = new ArrayList<String>();
        // Obtenemos todos los errores y los iteramos
        exception.getBindingResult().getFieldErrors() // Obtenemos todos los errores
                .forEach(error -> errors.add("El campo '" + error.getField() + "' " + error.getDefaultMessage())); // iteramos y agregamos cada error al listado

        // Devolvemos una instancia de "ErrorsResponse" que contiene una lista de errores.
        // "ErrorsResponse" es hijo de la clase "BaseErrorResponse" a retornar.
        return ErrorsResponse.builder()
                .errors(errors) // asignamos la lista de errores
                .status(HttpStatus.BAD_REQUEST.name()) // "BAD_REQUEST"
                .code(HttpStatus.BAD_REQUEST.value()) // 400
                .build();
    }
}
