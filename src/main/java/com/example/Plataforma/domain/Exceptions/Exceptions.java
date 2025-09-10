package com.example.Plataforma.domain.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class Exceptions extends Exception  {

    private final String mensagem;

    public Exceptions(String mensagem) {
        super(mensagem);        // garante que RuntimeException também armazena
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
