package com.example.Plataforma.API.DTO;

import java.time.LocalDateTime;

public class LucroDTO {

    public class lucroDTO {

        private Integer lucroH;
        private LocalDateTime data_premiacao;
        private String idcliente;

        public Integer getPremiacaoH() {
            return lucroH;
        }

        public void setPremiacaoH(Integer lucroH) {
            this.lucroH = lucroH;
        }

        public LocalDateTime getData_premiacao() {
            return data_premiacao;
        }
    }

}
