package com.example.demologin.CONTROL;
import com.example.demologin.DTO.ApostaRequest;
import com.example.demologin.DTO.UsuarioDTO;
import com.example.demologin.Model.Usuarios.JWTService;
import com.example.demologin.Model.Usuarios.JwtFilter;
import com.example.demologin.Model.Usuarios.Usuario;
import com.example.demologin.REPOSITORY.UsuarioRepository;
import com.example.demologin.SERVICE.PlataformaService;
import com.example.demologin.SERVICE.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/plataforma")
public class PlataformaController {

    @Autowired
    private PlataformaService plataformaService;

    @Autowired
    private final UsuarioRepository usuarioRepository;

    public PlataformaController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/apostar")
    public ResponseEntity<?> apostar(@RequestBody ApostaRequest request, HttpServletRequest requests) throws Exception {
        // Pega o email do usuário que o JwtFilter colocou como atributo

        String authHeader = requests.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            ResponseDto responseDto = new ResponseDto();
            responseDto.setErrorMessage("Seu token está vazio");
            ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                    .body(responseDto);
        }


        String token = authHeader.substring(7);
        String emailUsuario = JWTService.validarToken(token);
        usuarioRepository.findByEmail(emailUsuario);


        if (emailUsuario == null ) {
                ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                        .body("Usuário não autenticado");
            }

            // Chama o método de aposta
            String mensagem = plataformaService.apostar(emailUsuario, request.getValor());
            ResponseDto responseDto = new ResponseDto();
            responseDto.setSuccessMessage("Sua aposta foi criada com sucesso");
            responseDto.setNumAposta("90");

            return ResponseEntity.ok().body(responseDto);
    }
}


