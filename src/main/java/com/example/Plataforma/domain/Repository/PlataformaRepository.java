package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.Plataforma;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNome(String nome);
    default Plataforma findByNomeComLog(String nome) {
        Logger log = LoggerFactory.getLogger(PlataformaRepository.class);
        Plataforma plataforma = findByNome(nome).orElse(null);
        if (plataforma != null) {
            log.info("Plataforma encontrada: {}", nome);
        } else {
            log.warn("Plataforma não encontrada: {}", nome);
        }
        return plataforma;
    }

}
