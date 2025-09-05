package com.example.demologin.CONTROL;

import com.example.demologin.DTO.UsuarioDTO;

import com.example.demologin.Model.Usuarios.JWTService;
import com.example.demologin.Model.Usuarios.Usuario;
import com.example.demologin.REPOSITORY.UsuarioRepository;
import com.example.demologin.SERVICE.UsuarioService;
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

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastro(@RequestBody UsuarioDTO usuarioDTO, HttpServletRequest request) {

        try {

            String resposta = usuarioService.register(usuarioDTO);
            String token = jwtService.gerarToken(usuarioDTO.getEmail());
            request.setAttribute("usuario", usuarioDTO);
            usuarioRepository.findByEmail(usuarioDTO.getEmail());
            return ResponseEntity.ok().body("Registrado || Barrer " + token);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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



