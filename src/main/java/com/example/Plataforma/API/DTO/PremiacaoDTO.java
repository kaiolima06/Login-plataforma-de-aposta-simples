package com.example.Plataforma.API.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PremiacaoDTO {

    private Integer premiacaoH;
    private String data_premiacao;
    private Long idcliente;

    // Construtor vazio (necessário para frameworks como Spring Jackson)
    public PremiacaoDTO() {
    }

    // Construtor com premiação
    public PremiacaoDTO(Integer premiacaoH) {
        this.premiacaoH = premiacaoH;
    }

    // Construtor completo
    public PremiacaoDTO(Integer premiacaoH, LocalDateTime data_premiacao, long idcliente) {
        this.premiacaoH = premiacaoH;
        this.data_premiacao = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyy-MM-dd hh:mm:ss a"));;
        this.idcliente = idcliente;
    }

    // Getters e Setters
    public Integer getPremiacaoH() {
        return premiacaoH;
    }

    public void setPremiacaoH(Integer premiacaoH) {
        this.premiacaoH = premiacaoH;
    }

    public String getData_premiacao() {
        return data_premiacao;
    }

    public void setData_premiacao(String data_premiacao) {
        this.data_premiacao = data_premiacao;
    }

    public long getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(long idcliente) {
        this.idcliente = idcliente;
    }
}
