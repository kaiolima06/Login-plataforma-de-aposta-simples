package com.example.Plataforma.API.DTO;


import com.example.Plataforma.domain.model.Usuario;

public class UsuarioDTO {

        private Long id;

        private String nome;

        private String email;

        private String senha;

        private String cpf;

        private Boolean ativo;

        private Integer saldo;


       public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.saldo = usuario.getSaldo();
        this.email = usuario.getEmail();
        this.cpf = usuario.getCpf();
    }

    public  UsuarioDTO(String nome, Integer saldo, String email) {
        this.nome = nome;
        this.saldo = saldo;
        this.email = email;
    }

    public UsuarioDTO(Long id,
                      String nome,
                      String email,
                      Integer saldo) {
           this.id = id;
           this.nome = nome;
           this.email = email;
           this.saldo = saldo;
       }

    public UsuarioDTO(Long id, Integer saldo) {
           this.id = id;
           this.saldo = saldo;
    }

    public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSenha() {
            return senha;
        }

        public UsuarioDTO() {}

        public Long getId() {
        return id;
    }

        public int getSaldo() {
        return saldo;
    }

        public void setId(Long id) {
        this.id = id;
    }

        public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }

        public void setSenha(String senha) {
            this.senha = senha;
        }

        public String getCpf() {
            return cpf;
        }

        public void setCpf(String cpf) {
            this.cpf = cpf;
        }

        public void setAtivo(Boolean ativo){
            this.ativo = ativo;
        }

        public Boolean getAtivo() {
        return ativo;
    }
}


