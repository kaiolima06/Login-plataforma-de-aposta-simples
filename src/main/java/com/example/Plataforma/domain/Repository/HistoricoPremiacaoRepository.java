package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.HistoricoPremiacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoPremiacaoRepository extends JpaRepository<HistoricoPremiacao, Long> {
}

