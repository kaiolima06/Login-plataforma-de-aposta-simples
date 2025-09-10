package com.example.Plataforma.API.service;

import com.example.Plataforma.API.DTO.UsuarioDTO;
import com.example.Plataforma.domain.model.Usuario;
import com.example.Plataforma.domain.Exceptions.UserNotfound;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    public UsuarioService (UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {
        log.info("Iniciando Auth");
        return usuarioRepository.findByEmail(email)
                .filter(usuarioDTO -> usuarioDTO.getSenha().equals(senha.trim()))
                .orElseThrow(() ->  new UserNotfound("Usuario ou senha invalidos"));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public String register(UsuarioDTO usuarioDTO) {
        log.info("Buscando id");

        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            log.warn("Usuário ja existente");
            throw new UserNotfound("Usuário ja existe");
        }


        Usuario novousuario = new Usuario();
        novousuario.setEmail(usuarioDTO.getEmail());
        novousuario.setSenha(usuarioDTO.getSenha());
        novousuario.setCpf(usuarioDTO.getCpf());
        novousuario.setNome(usuarioDTO.getNome());
        usuarioRepository.save(novousuario);
        log.info("salvando usuario no banco");

        String resposta = "Login criado";
        return resposta;
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public List<UsuarioDTO> listarUsuarios() {

        return usuarioRepository.findAll()
                .stream()
                .map(u -> new UsuarioDTO(u.getNome(), u.getSaldo(), u.getEmail()))
                .toList();
    }
}