package com.tokseg.armariointeligente.services;

// Importações para envio de email real (descomentar quando configurar)
// import org.springframework.mail.SimpleMailMessage;
// import org.springframework.mail.javamail.JavaMailSender;
// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Marcar como serviço para ser injetável
public class EmailService {

    // @Autowired
    // private JavaMailSender mailSender; // Para envio real de email

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
        /*
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDestinatario);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        */

        // Simulação de envio para o console:
        System.out.println("----------------------------------------------------");
        System.out.println("Simulando Envio de Email:");
        System.out.println("Para: " + emailDestinatario);
        System.out.println("Assunto: " + subject);
        System.out.println("Corpo: \n" + body);
        System.out.println("----------------------------------------------------");
    }
}