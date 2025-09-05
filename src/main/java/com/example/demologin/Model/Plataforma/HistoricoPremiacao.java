package com.example.demologin.Model.Plataforma;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_premiacao")
public class HistoricoPremiacao {

    @Id
    @Column(name = "data_premio", nullable = false, updatable = false)
    private LocalDateTime dataPremio;

    private Integer premiacaoH = 0;

    // Construtores
    public HistoricoPremiacao() {}

    public HistoricoPremiacao(LocalDateTime dataPremio, Integer premiacaoH) {
        this.dataPremio = dataPremio;
        this.premiacaoH = premiacaoH;
    }

    public HistoricoPremiacao(Integer premiacaoH, LocalDateTime dataPremio) {
        this.dataPremio = dataPremio;
        this.premiacaoH = premiacaoH;
    }

    // Getters e Setters
    public LocalDateTime getDataPremio() {
        return dataPremio;
    }

    public void setDataPremio(LocalDateTime dataPremio) {
        this.dataPremio = dataPremio;
    }

    public Integer getPremiacaoH() {
        return premiacaoH;
    }

    public void setPremiacaoH(Integer premiacaoH) {
        this.premiacaoH = premiacaoH;
    }
}
