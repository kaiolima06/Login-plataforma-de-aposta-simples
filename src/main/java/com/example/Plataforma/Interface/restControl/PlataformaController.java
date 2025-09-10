package com.example.Plataforma.Interface.restControl;
import com.example.Plataforma.API.service.UsuarioService;
import com.example.Plataforma.domain.Exceptions.*;
import com.example.Plataforma.domain.model.Plataforma;
import com.example.Plataforma.API.DTO.ApostaResponse;
import com.example.Plataforma.Infra.config.JWTService;
import com.example.Plataforma.domain.Repository.PlataformaRepository;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import com.example.Plataforma.API.service.PlataformaService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/plataforma")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    @Autowired
    private final PlataformaRepository plataformaRepository;

    private static final Logger log = LoggerFactory.getLogger(PlataformaController.class);

    public PlataformaController(UsuarioRepository usuarioRepository, PlataformaRepository plataformaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.plataformaRepository = plataformaRepository;
    }

    @PostMapping("/apostar")
    public ResponseEntity<?> apostar(@RequestBody ApostaResponse response, HttpServletRequest requests) throws Exception {
        // Pega o email do usuário que o JwtFilter colocou como atributo

        log.info("Iniciando aposta");
        String authHeader = requests.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Erro de token: ");
            throw new TokenException("Problema com autenticação (token)");
        }

        log.info("Auth Header ok!");
        try {

                log.info("tentando logar");
                String token = authHeader.substring(7);
                String emailUsuario = JWTService.validarToken(token);
                usuarioRepository.findByEmail(emailUsuario);

                // Chama o serviço que já aposta e verifica premiação
                ApostaResponse resultado = plataformaService.apostar(emailUsuario, response.getValor());
                Optional<Plataforma> premiacaoDTO = plataformaRepository.findByNome("PADRAO");


                return ResponseEntity.ok(resultado);
                // Retorna 200 OK + mensagem

            }catch (TokenException e) {
            log.error("Erro de token: {}", e.getMensagem());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
            } catch (UserNotfound e) {
            log.warn("Usuário não encontrado: {}", e.getMensagem());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
            }catch (ApostaMinimaException e) {
            log.warn("Aposta mínima inválida: {}", e.getMessage());
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMensagem());
            }catch (SaldoException e) {
            log.warn("Saldo insuficiente: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMensagem());
            }catch (PlataformaException e) {
            log.warn("Erro na plataforma: {}", e.getMensagem());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
        } catch (IllegalArgumentException e) {
            log.warn("Aposta invalida {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApiException e) {
            log.error("ApiException: {}", e.getMensagem());
            return ResponseEntity.internalServerError().body("Erro de aplicação: " + e.getMensagem());
        } catch (Exception e) {
            log.error("exception: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
        }
    }



