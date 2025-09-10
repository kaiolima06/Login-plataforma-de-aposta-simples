package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.HistoricoLucro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricoLucroRepository extends JpaRepository<HistoricoLucro, Long> {
    default HistoricoLucro salvarComLog(HistoricoLucro historico) {
        HistoricoLucro salvo = save(historico);
        Logger log = LoggerFactory.getLogger(HistoricoLucroRepository.class);
        log.info("HistoricoLucro salvo com sucesso. ID={}, ValorLucro={}", salvo.getIdcliente(), salvo.getLucroH());
        return salvo;
    }
}