package com.example.demologin.Model.Plataforma;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_lucro")
public class HistoricoLucro {

    @Id
    @Column(name = "data_premio", nullable = false, updatable = false)
    private LocalDateTime dataLucro;

    private Integer lucroH = 0;

    // Construtores
    public HistoricoLucro() {}

    // Getters e Setters
    public LocalDateTime getDataLucro() {
        return dataLucro;
    }

    public void setDataLucro(LocalDateTime dataPremio) {
        this.dataLucro = dataPremio;
    }

    public Integer getLucroH() {
        return lucroH;
    }

    public void setLucroH(Integer lucroH) {
        this.lucroH = lucroH;
    }
}
