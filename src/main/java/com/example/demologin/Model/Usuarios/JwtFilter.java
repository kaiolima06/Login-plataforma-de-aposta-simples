package com.example.demologin.Model.Usuarios;
import com.example.demologin.DTO.UsuarioDTO;
import com.example.demologin.REPOSITORY.UsuarioRepository;
import com.example.demologin.SERVICE.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtFilter extends HttpFilter {

    @Autowired
    private final JWTService jwtService;

    @Autowired
    UsuarioRepository usuarioRepository;

    private final UsuarioService usuarioService;

    public JwtFilter(JWTService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain) throws IOException, ServletException {

        // Ignora o endpoint /login
        if (request.getServletPath().equals("/api/login")) {
            chain.doFilter(request, response);
            return;
        }

        // Pega o header Authorization
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token ausente ou inválido");
            return;
        }

        String token = authHeader.substring(7);

        if (authHeader != null) {
            try {
                String email = jwtService.validarToken(token);

                if (email != null) {
                    Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

                    if (usuarioOpt.isPresent()) {
                        Usuario usuario = usuarioOpt.get();

                        // Converte para DTO corretamente
                        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);

                        // Salva o usuário na request
                            request.setAttribute("usuario", usuarioDTO);
                    }
                }
            } catch (Exception e) {
                System.out.println("Token inválido: " + e.getMessage());
            }

            // Se passou, segue para o endpoint
            chain.doFilter(request, response);
        }
    }
}
