package com.example.Plataforma.Interface.restControl;

import com.example.Plataforma.domain.model.Usuario;
import com.example.Plataforma.API.DTO.UsuarioDTO;
import com.example.Plataforma.domain.Exceptions.ApiException;
import com.example.Plataforma.Infra.config.JWTService;
import com.example.Plataforma.domain.Exceptions.UserNotfound;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import com.example.Plataforma.API.service.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private final JWTService jwtUtil;
    @Autowired
    private final UsuarioRepository usuarioRepository;
    @Autowired
    UsuarioService usuarioService;

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(JWTService jwtUtil, UsuarioRepository usuarioRepository) {
        this.jwtUtil = jwtUtil;
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastro(@RequestBody UsuarioDTO usuarioDTO, HttpServletRequest request) {

        log.info("Iniciando cadastro");
        try {

            String resposta = usuarioService.register(usuarioDTO);
            String token = jwtUtil.gerarToken(usuarioDTO.getEmail());
            request.setAttribute("usuario", usuarioDTO);
            usuarioRepository.findByEmail(usuarioDTO.getEmail());
            return ResponseEntity.ok().body("Registrado || Barrer " + token);


        } catch (UserNotfound e) {
            log.warn("Usuário não encontrado: {}", e.getMensagem());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
        } catch (IllegalArgumentException e) {
            log.warn("Cadastro invalido {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApiException e) {
            log.error("ApiException: {}", e.getMensagem());
            // Qualquer outra exceção de domínio não prevista
            return ResponseEntity.internalServerError().body("Erro de aplicação: " + e.getMensagem());
        } catch (Exception e) {
            log.error("exception: {}", e.getMessage());
            // Qualquer exceção inesperada
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDTO loginRequest) {

        log.info("Iniciando login");
        try {
            Usuario user = usuarioService.autenticar(loginRequest.getEmail(), loginRequest.getSenha());
                String token = jwtUtil.gerarToken(loginRequest.getEmail());
                return ResponseEntity.ok().body(token);

        } catch (UserNotfound e) {
            log.warn("Usuário não encontrado: {}", e.getMensagem());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
        } catch (IllegalArgumentException e) {
            log.warn("Login invalido {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApiException e) {
            log.error("ApiException: {}", e.getMensagem());
            // Qualquer outra exceção de domínio não prevista
            return ResponseEntity.internalServerError().body("Erro de aplicação: " + e.getMensagem());
        } catch (Exception e) {
            log.error("exception: {}", e.getMessage());
            // Qualquer exceção inesperada
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
    }
}

