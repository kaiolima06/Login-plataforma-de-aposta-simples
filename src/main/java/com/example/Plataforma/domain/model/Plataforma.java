package com.example.Plataforma.domain.model;


import jakarta.persistence.*;

@Entity
@Table(name = "plataforma")
public class Plataforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idplataforma;

    private String nome;

    private Integer total;

    private Integer lucro;

    private Integer premiacao;

    public String getName() {
        return nome;
    }

    public void setName(String nome) {
        this.nome = nome;
    }

    public Long getPlataformaId() {
        return idplataforma;
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

    public boolean atingiuPremiacao() {
        return this.premiacao >= 100;
    }

    public void adicionarNoTotal(Integer valor) {
        this.total += valor;
    }

    public boolean atingiuLucro() {
        return this.total >= 100;
    }

    public void limparPremiacao(Integer premiacao) {
        this.premiacao = 0;
    }

    public boolean atingiuTotal() {
        return this.total >= 100;
    }
}
