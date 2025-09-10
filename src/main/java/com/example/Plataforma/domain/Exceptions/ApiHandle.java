package com.example.Plataforma.domain.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiHandle {

    @ExceptionHandler(UserNotfound.class)
    public ResponseEntity<ErrorResponse> handleUsers(UserNotfound usererror) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), usererror.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PremiacaoException.class)
    public ResponseEntity<ErrorResponse> handlePremiação(PremiacaoException premiacao) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), premiacao.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlataformaException.class)
    public ResponseEntity<ErrorResponse> handlePlataforma(PlataformaException plataforma) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), plataforma.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleToken(TokenException token) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), token.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class) // fallback para qualquer outra exceção
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(SaldoException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficiente(SaldoException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ApostaMinimaException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficiente(ApostaMinimaException ex) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apix) {
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), apix.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
