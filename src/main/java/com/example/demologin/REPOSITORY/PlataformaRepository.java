package com.example.demologin.REPOSITORY;

import com.example.demologin.Model.Plataforma.Plataforma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface PlataformaRepository extends JpaRepository<Plataforma, Long> {
    Optional<Plataforma> findByNome(String nome);

}
