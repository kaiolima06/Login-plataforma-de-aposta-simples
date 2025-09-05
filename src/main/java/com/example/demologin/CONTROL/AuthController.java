package com.example.demologin.CONTROL;

import com.example.demologin.DTO.UsuarioDTO;
import com.example.demologin.Model.Usuarios.JWTService;
import com.example.demologin.REPOSITORY.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final JWTService jwtUtil;
    private final UsuarioRepository usuarioRepository;

    public AuthController(JWTService jwtUtil, UsuarioRepository usuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {

        var usuarioOpt = usuarioRepository.findByEmailAndSenha(
                loginRequest.getEmail(),
                loginRequest.getSenha()
        );

        if (usuarioOpt.isPresent()) {
            String token = jwtUtil.gerarToken(loginRequest.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas");
        }
    }
}
