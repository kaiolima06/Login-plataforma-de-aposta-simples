package com.example.Plataforma.Infra.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JWTService {

    static SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRACAO = 1000 * 60 * 60;

    // Gerar token
    public static String gerarToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hora
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token
    public static String validarToken(String token) throws Exception {
//        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
// Deve se pegar a secretKey que foi gerada para validar,e n"ao gerar outra para validar, visto que ambas serão.



        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (SignatureException e) {
            System.out.println("Deu erro na validação do token, esse token foi alterado e não pode ser confiável");
            throw new IllegalArgumentException("Token JWT ausente ou mal formatado");
        } catch (MalformedJwtException e) {
            System.out.println("Esse token que vc enviou não é um token JWT válido, está malformatado");
            throw new IllegalArgumentException("Token JWT ausente ou mal formatado");
        }catch (Exception e) {
            throw new IllegalArgumentException("Token JWT ausente ou mal formatado");
        }
    }
}


