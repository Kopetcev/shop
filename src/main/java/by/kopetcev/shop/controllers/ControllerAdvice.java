package by.kopetcev.shop.controllers;

import by.kopetcev.shop.exception.ServiceException;
import by.kopetcev.shop.model.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Response> handleException(ServiceException e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, e.getError());
    }
}

