package com.example.Plataforma.domain.Repository;

import com.example.Plataforma.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findById(Long idusuario);

    Optional<Usuario> findByEmailAndSenha(String email, String senha);
}

