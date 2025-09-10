package com.example.Plataforma;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType; // ✅ IMPORT CORRETO
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class UsuarioControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testeCadastroSucesso() throws Exception {
        String json = """
            {
                "email": "teste@email.com",
                "senha": "123456",
                "cpf": "3425443"
            }
        """;

        mockMvc.perform(post("/api/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Cadastro feito com sucesso!"));
    }

    @Test
    public void testeCadastroEmailInvalido() throws Exception {
        String json = """
            {
                "email": "testeemail.com",
                "senha": "123456",
                "cpf": "09283098"
            }
        """;

        mockMvc.perform(post("/api/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email inválido, precisa conter @"));
    }
}
