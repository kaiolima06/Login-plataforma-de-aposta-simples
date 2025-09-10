package com.example.Plataforma.domain.model;

import jakarta.persistence.*;


@Entity
@Table(name = "useradmin")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idusuario;

    private String nome;

    private String email;

    @Column(nullable = false)
    private Integer saldo = 0;

    private String senha;

    private String cpf;



    // --- Getters ---

    public void setId(Long idusuario) {
        this.idusuario = idusuario;
    }

    public Long getId() { return idusuario; }

    public String getNome() { return nome; }

    public String getEmail() { return email; }

    public String getSenha() { return senha; }

    public String getCpf() { return cpf; }

    public Integer getSaldo() { return saldo; }

    // --- Setters ---
    public void setNome(String nome) { this.nome = nome; }

    public void setEmail(String email) { this.email = email; }

    public void setSenha(String senha) { this.senha = senha; }

    public void setCpf(String cpf) { this.cpf = cpf; }

    public void setSaldo(Integer saldo) { this.saldo = saldo; }

    // --- Métodos de saldo ---
    public void debitarSaldo(Integer valor) {
        if (getSaldo() < valor) {
            throw new RuntimeException("Saldo insuficiente"); // aqui
        }
        this.saldo -= valor;
    }

    public void creditarSaldo(Integer valor) {
        this.saldo += valor;
    }

}
