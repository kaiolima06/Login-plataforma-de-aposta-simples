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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PlataformaService {

    @Autowired
    private PlataformaRepository plataformaRepository;

    @Autowired
    HistoricoPremiacaoRepository historicoPremiacaoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    HistoricoLucroRepository historicoLucroRepository;


    public PlataformaService(UsuarioRepository usuarioRepository, PlataformaRepository plataformaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.plataformaRepository = plataformaRepository;
    }

    public boolean ganhou = false;

        @Transactional
        public ApostaResponse apostar(String emailUsuario, Integer valor) {
            //Buscar usuário
            Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                    .orElseThrow(() -> new UserNotfound("Usuário não encontrado"));


            //Validar valor
            if (valor == null || valor < 1) {
                throw new ApostaMinimaException("O valor da aposta deve ser maior que zero");
            }

            //Buscar plataforma padrão
            Plataforma plataforma = plataformaRepository.findByNome("PADRAO")
                    .orElseThrow(() -> new PlataformaException("Plataforma padrão não encontrada"));

            plataformaRepository.findById(plataforma.getPlataformaId())
                    .orElseThrow(() -> new PlataformaException("Plataforma padrão não encontrada"));

            //Validar saldo
            if (usuario.getSaldo() < valor) {
                throw new SaldoException(
                        "Saldo insuficiente! Seu saldo atual é: $" + usuario.getSaldo()
                );
            }

            //Registrar aposta
            usuario.debitarSaldo(valor);
            plataforma.adicionarNoTotal(valor);

            usuarioRepository.save(usuario);
            plataformaRepository.save(plataforma);

            String resposta = "Aposta feita";

            ApostaResponse apostaResponse = new ApostaResponse();
            apostaResponse.setResposta(resposta);
            apostaResponse.setValor(valor);
            return apostaResponse;

        }



    @Scheduled(fixedRate = 600000000)
    public void AtingiTotal() {
        // aqui você pode buscar todas as plataformas e aplicar a lógica
        Plataforma plataformas = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> new PlataformaException("Plataforma não encontrada"));

        if (plataformas.atingiuTotal()) {
            int percentual = 35;
            int valor = (int) (plataformas.getTotal() * (percentual / 100.0));

            plataformas.setPremiacao(plataformas.getPremiacao() + valor);
            plataformas.setTotal(plataformas.getTotal() - valor);


            plataformaRepository.save(plataformas);

            return;
        } else {
           return;
        }
    }

    @Scheduled(fixedRate = 100000000)
    public void AtingiLucro() {
        // aqui você pode buscar todas as plataformas e aplicar a lógica
        Plataforma plataformas = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));

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

                return;
                } else {

            return;
        }
    }
}


