package com.clickshop.suporte.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice // Indica que esta classe trata exceções globalmente
public class GlobalExceptionHandler {

    // Retorna HTTP 404 (Not Found)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFound(ResourceNotFoundException ex) {
        Map<String, String> errorResponse = Map.of("erro", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Retorna HTTP 400 (Bad Request) para regras de negócio
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<Map<String, String>> handleBusinessRule(BusinessRuleException ex) {
        Map<String, String> errorResponse = Map.of("erro", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Retorna HTTP 400 (Bad Request) para erros de validação (@NotBlank, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = Map.of(
                "erro", "Dados de entrada inválidos",
                "detalhes", ex.getBindingResult().getFieldErrors().stream()
                        .map(error -> Map.of(error.getField(), error.getDefaultMessage()))
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Handler genérico para qualquer outra exceção (HTTP 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = Map.of("erro", "Ocorreu um erro interno no servidor.");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}