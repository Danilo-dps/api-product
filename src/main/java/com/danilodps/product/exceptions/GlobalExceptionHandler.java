package com.danilodps.product.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Modelo padrão para respostas de erro
    private record ErrorResponse(
        LocalDateTime timestamp,
        String message,
        String errorType,
        int statusCode
    ) {}

    // 404 - Produto não encontrado
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleProductNotFound(ProductNotFoundException ex) {
        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "NOT_FOUND",
            HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 400 - Campo inválido na atualização
    @ExceptionHandler(InvalidFieldUpdateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidField(InvalidFieldUpdateException ex) {
        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "INVALID_FIELD",
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.badRequest().body(response);
    }

    // 400 - Preço inválido
    @ExceptionHandler(InvalidPriceException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPrice(InvalidPriceException ex) {
        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "INVALID_PRICE",
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.badRequest().body(response);
    }
    
 // 400 - Nome inválido
    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorResponse> handleInvalidName(InvalidNameException ex) {
        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            ex.getMessage(),
            "INVALID_NAME",
            HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.badRequest().body(response);
    }

    // Exemplo de tratamento genérico
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse response = new ErrorResponse(
            LocalDateTime.now(),
            "Ocorreu um erro interno no servidor",
            "INTERNAL_ERROR",
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return ResponseEntity.internalServerError().body(response);
    }
}