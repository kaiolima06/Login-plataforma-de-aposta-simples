package com.example.demologin.DTO;

public class ApostaRequest {

    private Long id;  // id do usuário ou da aposta
    private Integer valor;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
}
