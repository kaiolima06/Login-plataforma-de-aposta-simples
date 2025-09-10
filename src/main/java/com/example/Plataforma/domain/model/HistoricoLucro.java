package com.example.Plataforma.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_lucro")
public class HistoricoLucro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chave primária

    @Column(name = "data_lucro", nullable = false, updatable = false)
    private LocalDateTime dataLucro;

    private Integer lucroH = 0;

    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Usuario idcliente;

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

    public Usuario getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Usuario idcliente) {
        this.idcliente = idcliente;
    }
}
