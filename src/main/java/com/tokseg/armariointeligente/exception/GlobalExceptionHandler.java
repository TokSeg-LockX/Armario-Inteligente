package com.tokseg.armariointeligente.exception;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex,
            WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(), HttpStatus.NOT_FOUND.value(), "Não encontrado",
                        ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<?> resourceAlreadyExistsException(ResourceAlreadyExistsException ex,
            WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.CONFLICT.value(),
                "Conflito", ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
        ErrorResponse errorDetails =
                new ErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(), "Requisição inválida",
                        ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RelatedResourceException.class)
    public ResponseEntity<?> relatedResourceException(RelatedResourceException ex,
            WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(new Date(), HttpStatus.CONFLICT.value(),
                "Conflito", ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex,
            WebRequest request) {
        ValidationErrorResponse errorResponse =
                new ValidationErrorResponse(new Date(), HttpStatus.BAD_REQUEST.value(),
                        "Erro de validação", "Verifique os campos informados",
                        request.getDescription(false).replace("uri=", ""));

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errorResponse.addError(error.getField(), error.getDefaultMessage());
        }

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        ErrorResponse errorDetails = new ErrorResponse(new Date(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor",
                ex.getMessage(), request.getDescription(false).replace("uri=", ""));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
