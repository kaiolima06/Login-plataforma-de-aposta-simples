package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.HistoricoLucro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoLucroRepository extends JpaRepository<HistoricoLucro, Long> {
}