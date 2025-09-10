package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.HistoricoLucro;
import com.example.Plataforma.domain.model.HistoricoPremiacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoPremiacaoRepository extends JpaRepository<HistoricoPremiacao, Long> {
    default HistoricoPremiacao salvarComLog(HistoricoPremiacao historico) {
        HistoricoPremiacao salvo = save(historico);
        Logger log = LoggerFactory.getLogger(HistoricoLucroRepository.class);
        log.info("HistoricoLucro salvo com sucesso. ID={}, ValorLucro={}", salvo.getId(), salvo.getPremiacaoH());
        return salvo;
    }
}

