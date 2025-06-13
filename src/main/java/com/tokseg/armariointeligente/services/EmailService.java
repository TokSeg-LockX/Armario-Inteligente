package com.tokseg.armariointeligente.services;

// Importações para envio de email real (descomentar quando configurar)
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marcar como serviço para ser injetável
public class EmailService {

    @Autowired
    private JavaMailSender mailSender; // Para envio real de email

    public void enviarPinPorEmail(String emailDestinatario, String nomeDestinatario, String pin, String motivoAcesso, String codigoCompartimento) {
        String subject = "Seu PIN de Acesso para o Armário Inteligente TokSeg";
        String body = String.format(
                "Olá %s,\n\n" +
                        "Seu PIN para %s no compartimento %s é: %s\n\n" +
                        "Este PIN é válido por 2 horas.\n\n" +
                        "Atenciosamente,\nEquipe Armário Inteligente TokSeg",
                nomeDestinatario, motivoAcesso, codigoCompartimento, pin
        );

        // Lógica de envio de e-mail real aqui
        // Exemplo:

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("obrcrepper@gmail.com"); // Opcional: Defina um remetente ou configure spring.mail.from
            message.setTo(emailDestinatario);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
            System.out.println("Email enviado com sucesso para: " + emailDestinatario);
        } catch (Exception e) {
            System.err.println("Erro ao enviar email para: " + emailDestinatario + " - " + e.getMessage());
            // Adicionar um tratamento de erro mais robusto aqui (ex: logar a exceção completa)
            // ou relançar uma exceção customizada para ser tratada pelo chamador.
            // throw new RuntimeException("Falha ao enviar email do PIN.", e);
        }


        // Simulação de envio para o console:
        System.out.println("----------------------------------------------------");
        System.out.println("Simulando Envio de Email:");
        System.out.println("Para: " + emailDestinatario);
        System.out.println("Assunto: " + subject);
        System.out.println("Corpo: \n" + body);
        System.out.println("----------------------------------------------------");
    }
}