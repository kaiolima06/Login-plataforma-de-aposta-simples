package com.example.Plataforma.API.service;

import com.example.Plataforma.API.DTO.ApostaResponse;
import com.example.Plataforma.domain.model.HistoricoLucro;
import com.example.Plataforma.domain.model.Plataforma;
import com.example.Plataforma.domain.model.Usuario;
import com.example.Plataforma.domain.Exceptions.ApostaMinimaException;
import com.example.Plataforma.domain.Exceptions.PlataformaException;
import com.example.Plataforma.domain.Exceptions.SaldoException;
import com.example.Plataforma.domain.Exceptions.UserNotfound;
import com.example.Plataforma.domain.Repository.HistoricoLucroRepository;
import com.example.Plataforma.domain.Repository.HistoricoPremiacaoRepository;
import com.example.Plataforma.domain.Repository.PlataformaRepository;
import com.example.Plataforma.domain.Repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlataformaService {

    private static final Logger log = LoggerFactory.getLogger(PlataformaService.class);

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    private HistoricoPremiacaoRepository historicoPremiacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HistoricoLucroRepository historicoLucroRepository;

    public PlataformaService(UsuarioRepository usuarioRepository, PlataformaRepository plataformaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.plataformaRepository = plataformaRepository;
    }

    public boolean ganhou = false;

    @Transactional
    public ApostaResponse apostar(String emailUsuario, Integer valor) {
        log.info("Iniciando aposta. EmailUsuario={}, Valor={}", emailUsuario, valor);

        // Buscar usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado: {}", emailUsuario);
                    return new UserNotfound("Usuário não encontrado");
                });

        // Validar valor
        if (valor == null || valor < 1) {
            log.warn("Valor inválido para aposta. EmailUsuario={}, Valor={}", emailUsuario, valor);
            throw new ApostaMinimaException("O valor da aposta deve ser maior que zero");
        }

        // Buscar plataforma padrão
        Plataforma plataforma = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> {
                    log.error("Plataforma padrão não encontrada");
                    return new PlataformaException("Plataforma padrão não encontrada");
                });

        plataformaRepository.findById(plataforma.getPlataformaId())
                .orElseThrow(() -> {
                    log.error("Plataforma padrão não encontrada pelo ID {}", plataforma.getPlataformaId());
                    return new PlataformaException("Plataforma padrão não encontrada");
                });

        // Validar saldo
        if (usuario.getSaldo() < valor) {
            log.warn("Saldo insuficiente. EmailUsuario={}, SaldoAtual={}, ValorAposta={}",
                    emailUsuario, usuario.getSaldo(), valor);
            throw new SaldoException(
                    "Saldo insuficiente! Seu saldo atual é: $" + usuario.getSaldo()
            );
        }

        // Registrar aposta
        usuario.debitarSaldo(valor);
        plataforma.adicionarNoTotal(valor);
        usuarioRepository.save(usuario);
        plataformaRepository.save(plataforma);

        log.info("Aposta registrada com sucesso. EmailUsuario={}, Valor={}, NovoSaldo={}, TotalPlataforma={}",
                emailUsuario, valor, usuario.getSaldo(), plataforma.getTotal());

        ApostaResponse apostaResponse = new ApostaResponse();
        apostaResponse.setResposta("Aposta feita");
        apostaResponse.setValor(valor);
        return apostaResponse;
    }

    @Scheduled(fixedRate = 600000000)
    public void AtingiTotal() {
        log.info("Verificando se a plataforma atingiu o total para premiação...");

        Plataforma plataformas = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> {
                    log.error("Plataforma não encontrada ao verificar premiação.");
                    return new PlataformaException("Plataforma não encontrada");
                });

        if (plataformas.atingiuTotal()) {
            int percentual = 35;
            int valor = (int) (plataformas.getTotal() * (percentual / 100.0));

            plataformas.setPremiacao(plataformas.getPremiacao() + valor);
            plataformas.setTotal(plataformas.getTotal() - valor);

            plataformaRepository.save(plataformas);

            log.info("Premiação aplicada. Percentual={}%, ValorPremiacao={}, TotalAtual={}",
                    percentual, valor, plataformas.getTotal());

        } else {
            log.debug("Plataforma ainda não atingiu o total para premiação.");
        }
    }

    @Scheduled(fixedRate = 100000000)
    public void AtingiLucro() {
        log.info("Verificando se a plataforma atingiu o total para calcular lucro...");

        Plataforma plataformas = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> {
                    log.error("Plataforma não encontrada ao verificar lucro.");
                    return new RuntimeException("Plataforma não encontrada");
                });

        if (plataformas.atingiuTotal()) {
            int percentual = 35;
            int lucroH = (int) (plataformas.getTotal() * (percentual / 100.0));

            plataformas.setLucro(lucroH);
            plataformas.setTotal(plataformas.getTotal() - lucroH);
            plataformaRepository.save(plataformas);

            // Salva histórico
            HistoricoLucro historico = new HistoricoLucro();
            historico.setDataLucro(historico.getDataLucro());
            historico.setLucroH(lucroH);
            historicoLucroRepository.save(historico);

            log.info("Lucro registrado. Percentual={}%, ValorLucro={}, TotalAtual={}",
                    percentual, lucroH, plataformas.getTotal());

        } else {
            log.debug("Plataforma ainda não atingiu o total para lucro.");
        }
    }
}
