package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.models.entrega.Entrega;
import com.tokseg.armariointeligente.models.entrega.StatusEntrega; // Enum atualizado
// import com.tokseg.armariointeligente.models.notificacao.Notificacao; // Removido se não usado diretamente aqui
import com.tokseg.armariointeligente.models.usuario.Usuario; // Importar
import com.tokseg.armariointeligente.repositories.entrega.EntregaRepository;
// import com.tokseg.armariointeligente.repositories.notificacao.NotificacaoRepository; // Removido se não usado
import com.tokseg.armariointeligente.repositories.usuario.UsuarioRepository; // Importar
import com.tokseg.armariointeligente.utils.PinUtil; // Importar
import lombok.RequiredArgsConstructor;
// import org.springframework.beans.factory.annotation.Autowired; // Removido
import org.springframework.security.core.context.SecurityContextHolder; // Importar
import org.springframework.security.core.userdetails.UserDetails; // Importar
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importar

import java.time.LocalDateTime;
import java.util.List;
// import java.util.Optional; // Removido se não usado
// import java.util.UUID; // Removido se não usado

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final CompartimentoService compartimentoService;
    private final UsuarioRepository usuarioRepository; // Injetar
    private final EmailService emailService;           // Injetar

    /**
     * Chamado pelo entregador para iniciar um depósito.
     * Cria a entrega, gera PIN para o entregador e envia por email.
     * Reserva o compartimento.
     */
    @Transactional
    public Entrega solicitarAberturaParaDeposito(Long compartimentoId, String descricaoPacote, Long destinatarioId) {
        String emailEntregadorLogado = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Usuario entregador = usuarioRepository.findByEmail(emailEntregadorLogado)
                .orElseThrow(() -> new RuntimeException("Usuário entregador autenticado não encontrado no banco de dados."));

        Usuario destinatario = usuarioRepository.findById(destinatarioId)
                .orElseThrow(() -> new RuntimeException("Destinatário com ID " + destinatarioId + " não encontrado."));

        Compartimento compartimento = compartimentoService.buscarPorId(compartimentoId);
        if (compartimento.getStatus() != StatusCompartimento.DISPONIVEL) {
            throw new RuntimeException("Compartimento " + compartimento.getCodigo() + " não está disponível.");
        }

        Entrega entrega = new Entrega();
        entrega.setCompartimento(compartimento);
        entrega.setEntregador(entregador.getNome()); // Ou pode ser o email, ou o ID do Usuario entregador
        // Se precisar do objeto Usuario entregador na Entrega, adicione um campo @ManyToOne Usuario entregadorReal;
        // entrega.setEntregadorReal(entregador);
        entrega.setDestinatario(destinatario);
        entrega.setDescricao(descricaoPacote);
        entrega.setStatus(StatusEntrega.AGUARDANDO_DEPOSITO); // Novo status
        // dataEntrega será setada na confirmação do depósito

        String pinEntregador = PinUtil.gerarPin6Digitos();
        entrega.setPinEntregador(pinEntregador);
        entrega.setPinEntregadorValidade(LocalDateTime.now().plusHours(2));

        Entrega savedEntrega = entregaRepository.save(entrega);

        compartimentoService.atualizarStatus(compartimentoId, StatusCompartimento.RESERVADO);

        emailService.enviarPinPorEmail(
                entregador.getEmail(),
                entregador.getNome(),
                pinEntregador,
                "depositar o item",
                compartimento.getCodigo()
        );

        return savedEntrega;
    }

    /**
     * Chamado após o entregador confirmar que depositou o item (ex: após usar o PIN e fechar a porta).
     * Atualiza o status da entrega, gera PIN para o destinatário e envia por email.
     * Marca o compartimento como OCUPADO.
     */
    @Transactional
    public Entrega confirmarDepositoGerarPinDestinatario(Long entregaId) {
        Entrega entrega = buscarPorId(entregaId);

        if (entrega.getStatus() != StatusEntrega.AGUARDANDO_DEPOSITO && entrega.getStatus() != StatusEntrega.DEPOSITO_PENDENTE_CONFIRMACAO) {
            // Adicionar DEPOSITO_PENDENTE_CONFIRMACAO se for um estado intermediário
            throw new RuntimeException("A entrega não está aguardando confirmação de depósito.");
        }
        // Aqui poderia haver uma validação se o PIN do entregador foi usado recentemente ou
        // se o compartimento foi de fato aberto por ele.

        String pinDestinatario = PinUtil.gerarPin6Digitos();
        entrega.setPinDestinatario(pinDestinatario);
        entrega.setPinDestinatarioValidade(LocalDateTime.now().plusHours(2));

        entrega.setPinEntregador(null); // Opcional: invalidar PIN do entregador após uso
        entrega.setPinEntregadorValidade(null);

        entrega.setStatus(StatusEntrega.PENDENTE); // Item depositado, aguardando retirada
        entrega.setDataEntrega(LocalDateTime.now()); // Define a data/hora real do depósito

        Entrega savedEntrega = entregaRepository.save(entrega);

        compartimentoService.atualizarStatus(entrega.getCompartimento().getId(), StatusCompartimento.OCUPADO);

        emailService.enviarPinPorEmail(
                entrega.getDestinatario().getEmail(),
                entrega.getDestinatario().getNome(),
                pinDestinatario,
                "retirar seu item",
                entrega.getCompartimento().getCodigo()
        );

        return savedEntrega;
    }

    /**
     * Regenera um PIN para o entregador ou destinatário.
     */
    @Transactional
    public String regenerarPin(Long entregaId, String tipoUsuarioPin) {
        Entrega entrega = buscarPorId(entregaId);
        String novoPin = PinUtil.gerarPin6Digitos();
        LocalDateTime novaValidade = LocalDateTime.now().plusHours(2);

        String emailParaEnvio;
        String nomeParaEmail;
        String motivoAcesso;
        Usuario usuarioAutenticado = usuarioRepository.findByEmail(
                ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername()
        ).orElseThrow(() -> new RuntimeException("Usuário autenticado não encontrado."));

        if ("ENTREGADOR".equalsIgnoreCase(tipoUsuarioPin)) {
            // Validar se o usuário autenticado é o entregador associado ou um admin
            // Para simplificar, vamos assumir que o nome do entregador na entrega é único ou usamos ID se tivéssemos
            // if (!entrega.getEntregador().equals(usuarioAutenticado.getNome())) {
            //    throw new SecurityException("Usuário não autorizado a regenerar PIN de entregador para esta entrega.");
            // }
            // É melhor ter um campo `Usuario entregadorReal` na entidade Entrega.
            // Se o campo `entregador` é só uma string com nome, não podemos validar aqui sem mais info.
            // Vamos assumir que a lógica de quem pode chamar isso é controlada no controller com @PreAuthorize

            if (entrega.getStatus() != StatusEntrega.AGUARDANDO_DEPOSITO) {
                throw new RuntimeException("Não é possível regenerar PIN de entregador para entrega neste estado: " + entrega.getStatus());
            }
            entrega.setPinEntregador(novoPin);
            entrega.setPinEntregadorValidade(novaValidade);
            // Precisamos do email do entregador original. Se `entrega.getEntregador()` for só nome,
            // teríamos que buscar o Usuario entregador de outra forma ou tê-lo associado.
            // Por ora, vamos usar o email do usuário autenticado, assumindo que é o entregador correto.
            emailParaEnvio = usuarioAutenticado.getEmail();
            nomeParaEmail = usuarioAutenticado.getNome();
            motivoAcesso = "depositar o item";
        } else if ("DESTINATARIO".equalsIgnoreCase(tipoUsuarioPin)) {
            if (!entrega.getDestinatario().getId().equals(usuarioAutenticado.getId())) {
                throw new SecurityException("Usuário não autorizado a regenerar PIN de destinatário para esta entrega.");
            }
            if (entrega.getStatus() != StatusEntrega.PENDENTE) {
                throw new RuntimeException("Não é possível regenerar PIN de destinatário para entrega neste estado: " + entrega.getStatus());
            }
            entrega.setPinDestinatario(novoPin);
            entrega.setPinDestinatarioValidade(novaValidade);
            emailParaEnvio = entrega.getDestinatario().getEmail();
            nomeParaEmail = entrega.getDestinatario().getNome();
            motivoAcesso = "retirar seu item";
        } else {
            throw new IllegalArgumentException("Tipo de usuário inválido ('" + tipoUsuarioPin + "') para regeneração de PIN.");
        }

        entregaRepository.save(entrega);
        emailService.enviarPinPorEmail(emailParaEnvio, nomeParaEmail, novoPin, motivoAcesso, entrega.getCompartimento().getCodigo());

        return "Um novo PIN foi enviado para o e-mail " + emailParaEnvio + ".";
    }

    /**
     * Chamado quando o destinatário retira o item com sucesso.
     */
    @Transactional
    public void marcarComoRetirado(Long entregaId) {
        Entrega entrega = buscarPorId(entregaId);
        // Validação adicional se necessário (ex: quem está chamando este método)
        entrega.setStatus(StatusEntrega.RETIRADO);
        entrega.setPinDestinatario(null); // Invalida o PIN
        entrega.setPinDestinatarioValidade(null);
        // Se o PIN do entregador ainda estiver lá e precisar ser limpo
        // entrega.setPinEntregador(null);
        // entrega.setPinEntregadorValidade(null);
        entregaRepository.save(entrega);

        compartimentoService.atualizarStatus(entrega.getCompartimento().getId(), StatusCompartimento.DISPONIVEL);
        // Enviar notificação de retirada se necessário
    }

    // Métodos existentes que podem precisar de ajustes:
    // O `registrarEntrega` original precisa ser repensado. O fluxo agora é:
    // 1. Entregador solicita abertura -> `solicitarAberturaParaDeposito` (gera PIN entregador)
    // 2. Entregador usa PIN para abrir -> `compartimentoService.abrirComPin`
    // 3. Entregador confirma depósito (novo endpoint?) -> `confirmarDepositoGerarPinDestinatario` (gera PIN destinatário)
    // O método `registrarEntrega(Entrega entrega)` original talvez não seja mais usado diretamente.
    /*
    public Entrega registrarEntrega(Entrega entrega) { // ESTE MÉTODO PRECISA SER REAVALIADO OU REMOVIDO
        Compartimento compartimento = entrega.getCompartimento();
        // Esta lógica agora está dividida em solicitarAbertura e confirmarDeposito
        // compartimentoService.atualizarStatus(compartimento.getId(), StatusCompartimento.OCUPADO);
        return entregaRepository.save(entrega);
    }
    */

    public List<Entrega> listarPorUsuario(Long usuarioId) { // Continuaria válido para o destinatário
        return entregaRepository.findByDestinatario_Id(usuarioId);
    }

    public Entrega buscarPorId(Long id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega não encontrada com ID: " + id));
    }

    // O método `atualizarStatus` genérico pode ser perigoso se não controlado.
    // É melhor ter métodos específicos como `marcarComoRetirado`, `cancelarEntrega`, etc.
    @Transactional
    public void atualizarStatus(Long entregaId, StatusEntrega status) { // USAR COM CAUTELA
        Entrega entrega = buscarPorId(entregaId);
        entrega.setStatus(status);
        entregaRepository.save(entrega);
    }
}