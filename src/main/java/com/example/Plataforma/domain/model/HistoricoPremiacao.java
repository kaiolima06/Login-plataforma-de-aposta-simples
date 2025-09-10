package com.example.Plataforma.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historico_premiacao")
public class HistoricoPremiacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // chave primária

    @Column(name = "data_premio", nullable = false, updatable = false)
    private LocalDateTime dataPremio;


    @Column(name = "premiacaoH", nullable = false)
    private Integer premiacaoH = 0;

    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Usuario idcliente;

    // Construtor vazio (obrigatório para JPA)
    public HistoricoPremiacao() {}

    // Construtor completo
    public HistoricoPremiacao(Integer premiacaoH, LocalDateTime dataPremio, Usuario idcliente) {
        this.premiacaoH = premiacaoH;
        this.dataPremio = dataPremio;
        this.idcliente = idcliente;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

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

    public Usuario getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(Long id) {
        this.idcliente = idcliente;
    }

}
