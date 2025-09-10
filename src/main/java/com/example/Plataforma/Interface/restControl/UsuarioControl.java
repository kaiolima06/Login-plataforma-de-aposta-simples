package com.example.Plataforma.Interface.restControl;

import com.example.Plataforma.API.DTO.UsuarioDTO;

import com.example.Plataforma.Infra.config.JWTService;
import com.example.Plataforma.domain.model.Usuario;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import com.example.Plataforma.API.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UsuarioControl {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    JWTService jwtService;

    @PostMapping("/api/usuarios/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO, HttpServletRequest request) {

        Usuario usuario = usuarioService.autenticar(usuarioDTO.getEmail(), usuarioDTO.getSenha());
        // Salva o usuário na request

        if (usuario == null || !usuarioDTO.getSenha().equals(usuario.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha inválidos");
        }

        if (usuario.getEmail() != null) {
            request.setAttribute("usuario", usuarioDTO);
            String token = jwtService.gerarToken(usuarioDTO.getEmail());
            usuarioRepository.findByEmail(usuario.getEmail());
            return ResponseEntity.ok().body("tpken login:" + token );
        }

        // Caso o email seja null
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Falha no login");
    }


    @GetMapping("/meus-dados")
    public Usuario listarDados(HttpServletRequest request) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Token não informado");
        }

        String token = authHeader.substring(7);
        String email = jwtService.validarToken(token); // retorna email do usuário do token

        return usuarioService.buscarPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }
}



