package com.example.demologin.REPOSITORY;

import com.example.demologin.Model.Plataforma.HistoricoLucro;
import com.example.demologin.Model.Plataforma.HistoricoPremiacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoLucroRepository extends JpaRepository<HistoricoLucro, Long> {
}