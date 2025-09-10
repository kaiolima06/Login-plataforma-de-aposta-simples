package com.example.Plataforma.API.DTO;

public class PlataformaDTO {

    private long plataformaId;

    private Integer total;

    private Integer lucro;

    private Integer premiacao;

    private Integer valor;

    public int getValor() { return valor; }
    public void setValor(Integer valor) { this.valor = valor; }

    public long getPlataformaId() {
        return plataformaId;
    }

    public void setPlataformaId(long plataformaId) {
        this.plataformaId = plataformaId;
    }

    public int getLucro() {
        return lucro;
    }

    public void setLucro(Integer lucro) {
        this.lucro = lucro;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public int getPremiacao() {
        return premiacao;
    }

    public void setPremiacao(Integer premiacao) {
        this.premiacao = premiacao;
    }

}

