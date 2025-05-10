package com.tokseg.armariointeligente.config;

import com.tokseg.armariointeligente.models.armario.Armario;
import com.tokseg.armariointeligente.models.armario.StatusArmario;
import com.tokseg.armariointeligente.models.compartimento.Compartimento;
import com.tokseg.armariointeligente.models.compartimento.StatusCompartimento;
import com.tokseg.armariointeligente.models.compartimento.TamanhoCompartimento;
import com.tokseg.armariointeligente.models.usuario.TipoUsuario;
import com.tokseg.armariointeligente.models.usuario.Usuario;
import com.tokseg.armariointeligente.repositories.armario.ArmarioRepository;
import com.tokseg.armariointeligente.repositories.compartimento.CompartimentoRepository;
import com.tokseg.armariointeligente.repositories.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ArmarioRepository armarioRepository;
    private final CompartimentoRepository compartimentoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@tokseg.com");
            admin.setTelefone("79999999999");
            admin.setSenha(passwordEncoder.encode("admin123"));
            admin.setTipo(TipoUsuario.ADMIN);
            usuarioRepository.save(admin);

            Usuario morador = new Usuario();
            morador.setNome("Maria Moradora");
            morador.setEmail("maria@tokseg.com");
            morador.setTelefone("79988887777");
            morador.setSenha(passwordEncoder.encode("morador123"));
            morador.setTipo(TipoUsuario.MORADOR);
            usuarioRepository.save(morador);

            Usuario porteiro = new Usuario();
            porteiro.setNome("Pedro Porteiro");
            porteiro.setEmail("porteiro@tokseg.com");
            porteiro.setTelefone("79977776666");
            porteiro.setSenha(passwordEncoder.encode("porteiro123"));
            porteiro.setTipo(TipoUsuario.PORTEIRO);
            usuarioRepository.save(porteiro);

            Usuario entregador = new Usuario();
            entregador.setNome("Enzo Entregador");
            entregador.setEmail("entregador@tokseg.com");
            entregador.setTelefone("79966665555");
            entregador.setSenha(passwordEncoder.encode("entrega123"));
            entregador.setTipo(TipoUsuario.ENTREGADOR);
            usuarioRepository.save(entregador);
        }

        if (armarioRepository.count() == 0) {
            Armario armario = new Armario();
            armario.setLocalizacao("Bloco A - Entrada");
            armario.setQuantidadeCompartimento(3);
            armario.setStatus(StatusArmario.ATIVO);
            armarioRepository.save(armario);

            for (int i = 1; i <= 3; i++) {
                Compartimento c = new Compartimento();
                c.setArmario(armario);
                c.setCodigo("A" + i);
                c.setStatus(StatusCompartimento.DISPONIVEL);
                c.setTamanho(TamanhoCompartimento.MEDIO);
                compartimentoRepository.save(c);
            }
        }

        System.out.println("✅ Dados iniciais carregados com sucesso.");
    }
}

