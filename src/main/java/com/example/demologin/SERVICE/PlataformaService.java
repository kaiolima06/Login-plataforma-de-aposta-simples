package com.example.demologin.SERVICE;
import com.example.demologin.Model.Plataforma.HistoricoLucro;
import com.example.demologin.Model.Plataforma.HistoricoPremiacao;
import com.example.demologin.Model.Plataforma.Plataforma;
import com.example.demologin.Model.Usuarios.Usuario;
import com.example.demologin.REPOSITORY.HistoricoLucroRepository;
import com.example.demologin.REPOSITORY.HistoricoPremiacaoRepository;
import com.example.demologin.REPOSITORY.PlataformaRepository;
import com.example.demologin.REPOSITORY.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
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
    public String apostar(String emailUsuario, Integer valor) {
        //Buscar usuário
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        //Validar valor
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da aposta deve ser maior que zero");
        }

        //Buscar plataforma padrão
        Plataforma plataforma = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> new EntityNotFoundException("Plataforma padrão não encontrada"));

        //Validar saldo
        if (usuario.getSaldo() < valor) {
            throw new IllegalArgumentException(
                    "Saldo insuficiente! Seu saldo atual é: " + usuario.getSaldo()
            );
        }

        //Registrar aposta
        usuario.debitarSaldo(valor);
        plataforma.adicionarNoTotal(valor);

        usuarioRepository.save(usuario);
        plataformaRepository.save(plataforma);

        //Checar premiação
        if (plataforma.atingiuPremiacao()) {
            int premio = plataforma.getPremiacao();

            usuario.creditarSaldo(premio);
            plataforma.limparPremiacao(premio);

            usuarioRepository.save(usuario);
            plataformaRepository.save(plataforma);

            HistoricoPremiacao historico = new HistoricoPremiacao();
            historico.setPremiacaoH(premio);
            historicoPremiacaoRepository.save(historico);

            return "🎉 Parabéns " + usuario.getNome() +
                    "! Você ganhou uma premiação de R$ " + premio +
                    ". Seu novo saldo é: R$ " + usuario.getSaldo();
        }

        return "✅ Aposta de R$ " + valor + " registrada com sucesso! " +
                "Saldo atual: R$ " + usuario.getSaldo();
    }

    @Scheduled(fixedRate = 600000000)
    public void AtingiTotal() {
        // aqui você pode buscar todas as plataformas e aplicar a lógica
        Plataforma plataformas = plataformaRepository.findByNome("PADRAO")
                .orElseThrow(() -> new RuntimeException("Plataforma não encontrada"));

        if (!plataformas.atingiuTotal()) {
            System.out.println("Total baixo para a plataforma, Total: $$" + plataformas.getTotal());
            return;
        } else {
            int percentual = 35;
            int valor = (int) (plataformas.getTotal() * (percentual / 100.0));

            plataformas.setTotal(plataformas.getTotal() - valor);
            plataformas.setPremiacao(plataformas.getPremiacao() + valor);

            plataformaRepository.save(plataformas);

            System.out.println("Premiação saindo...");
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

                    plataformas.setPremiacao(lucroH);
                    plataformas.setTotal(plataformas.getTotal() - lucroH);

                    plataformaRepository.save(plataformas);

                    // Salva histórico
                    HistoricoLucro historico = new HistoricoLucro();
                    historico.setDataLucro(historico.getDataLucro());
                    historico.setLucroH(lucroH);
                    historicoLucroRepository.save(historico);

                System.out.println("Lucro captado..." + lucroH);
                } else {

            System.out.println("Total baixo..." + plataformas.getTotal());
        }
    }
}


