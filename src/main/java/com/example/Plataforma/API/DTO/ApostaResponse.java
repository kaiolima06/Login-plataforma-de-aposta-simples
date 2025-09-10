package com.example.Plataforma.API.DTO;

public class ApostaResponse {

    private String resposta;

    private Integer valor = 0;

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public ApostaResponse () {
        this.resposta = resposta;

    }
    public int getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }
}

