package com.example.Plataforma.domain.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TokenException extends RuntimeException  {

    private final String mensagem;

    public TokenException(String mensagem) {
        super(mensagem);        // garante que RuntimeException também armazena
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
    }
