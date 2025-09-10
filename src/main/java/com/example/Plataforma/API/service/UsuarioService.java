package com.example.Plataforma.API.service;

import com.example.Plataforma.API.DTO.UsuarioDTO;
import com.example.Plataforma.domain.model.Usuario;
import com.example.Plataforma.domain.Exceptions.UserNotfound;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    public UsuarioService (UsuarioRepository usuarioRepository) {

        this.usuarioRepository = usuarioRepository;
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioRepository.findByEmail(email)
                .filter(usuarioDTO -> usuarioDTO.getSenha().equals(senha.trim()))
                .orElseThrow(() -> new UserNotfound("Usuario ou senha invalidos"));
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public String register(UsuarioDTO usuarioDTO) {

        if (usuarioRepository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            throw new UserNotfound("Usuário ja existe");
        }


        Usuario novousuario = new Usuario();
        novousuario.setEmail(usuarioDTO.getEmail());
        novousuario.setSenha(usuarioDTO.getSenha());
        novousuario.setCpf(usuarioDTO.getCpf());
        novousuario.setNome(usuarioDTO.getNome());
        usuarioRepository.save(novousuario);

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