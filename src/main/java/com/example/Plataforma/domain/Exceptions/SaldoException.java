package com.example.Plataforma.domain.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SaldoException extends RuntimeException  {

    private final String mensagem;


    public SaldoException(String mensagem) {
        super(mensagem);        // garante que RuntimeException também armazena
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}
