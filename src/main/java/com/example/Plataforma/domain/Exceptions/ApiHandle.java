package com.example.Plataforma.domain.Exceptions;

import com.example.Plataforma.API.service.PlataformaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiHandle {

    private static final Logger log = LoggerFactory.getLogger(ApiHandle.class);

    @ExceptionHandler(UserNotfound.class)
    public ResponseEntity<ErrorResponse> handleUsers(UserNotfound usererror) {
        log.warn("Usuário não encontrado: {}", usererror.getMensagem());
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), usererror.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PremiacaoException.class)
    public ResponseEntity<ErrorResponse> handlePremiação(PremiacaoException premiacao) {
        log.warn("Erro de premiação: {}", premiacao.getMensagem());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), premiacao.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlataformaException.class)
    public ResponseEntity<ErrorResponse> handlePlataforma(PlataformaException plataforma) {
        log.warn("Erro na plataforma: {}", plataforma.getMensagem());
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), plataforma.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleToken(TokenException token) {
        log.error("Erro de token: {}", token.getMensagem(), token);
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND.value(), token.getMensagem());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class) // fallback para qualquer outra exceção
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Erro genérico inesperado: {}", ex.getMessage(), ex);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(SaldoException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficiente(SaldoException ex) {
        log.warn("Saldo insuficiente: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ApostaMinimaException.class)
    public ResponseEntity<ErrorResponse> handleSaldoInsuficiente(ApostaMinimaException ex) {
        log.warn("Aposta mínima inválida: {}", ex.getMessage());
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleApiException(ApiException apix) {
        log.error("Erro na API: {}", apix.getMessage(), apix);
        ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), apix.getMessage());
        return ResponseEntity.badRequest().body(error);
    }
}
