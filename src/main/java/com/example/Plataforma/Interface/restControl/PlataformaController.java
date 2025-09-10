package com.example.Plataforma.Interface.restControl;
import com.example.Plataforma.domain.Exceptions.*;
import com.example.Plataforma.domain.model.Plataforma;
import com.example.Plataforma.API.DTO.ApostaResponse;
import com.example.Plataforma.Infra.config.JWTService;
import com.example.Plataforma.domain.Repository.PlataformaRepository;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import com.example.Plataforma.API.service.PlataformaService;
import jakarta.servlet.http.HttpServletRequest;
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

    public PlataformaController(UsuarioRepository usuarioRepository, PlataformaRepository plataformaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.plataformaRepository = plataformaRepository;
    }

    @PostMapping("/apostar")
    public ResponseEntity<?> apostar(@RequestBody ApostaResponse response, HttpServletRequest requests) throws Exception {
        // Pega o email do usuário que o JwtFilter colocou como atributo

        String authHeader = requests.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new TokenException("Problema com autenticação (token)");
        }

        try {

                String token = authHeader.substring(7);
                String emailUsuario = JWTService.validarToken(token);
                usuarioRepository.findByEmail(emailUsuario);

                // Chama o serviço que já aposta e verifica premiação
                ApostaResponse resultado = plataformaService.apostar(emailUsuario, response.getValor());
                Optional<Plataforma> premiacaoDTO = plataformaRepository.findByNome("PADRAO");


                return ResponseEntity.ok(resultado);
                // Retorna 200 OK + mensagem

            }catch (TokenException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
            } catch (UserNotfound e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
            }catch (ApostaMinimaException e) {
                return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMensagem());
            }catch (SaldoException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMensagem());
            }catch (PlataformaException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMensagem());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ApiException e) {
            return ResponseEntity.internalServerError().body("Erro de aplicação: " + e.getMensagem());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Erro inesperado: " + e.getMessage());
        }
        }
    }



