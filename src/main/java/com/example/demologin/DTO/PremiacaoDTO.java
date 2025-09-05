package com.example.demologin.DTO;

import java.time.LocalDateTime;
import java.util.Date;

public class PremiacaoDTO {

    private Integer premiacaoH;

    private LocalDateTime data_premiacao;

    public Integer getPremiacaoH() {
        return premiacaoH;
    }

    public void setPremiacaoH(Integer premiacaoH) {
        this.premiacaoH = premiacaoH;
    }

    public LocalDateTime getData_premiacao() {
        return data_premiacao;
    }
}
