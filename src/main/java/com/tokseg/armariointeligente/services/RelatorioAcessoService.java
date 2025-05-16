package com.tokseg.armariointeligente.services;

import com.tokseg.armariointeligente.models.relatorioacesso.RelatorioAcesso;
import com.tokseg.armariointeligente.repositories.relatorioacesso.RelatorioAcessoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RelatorioAcessoService {

    private final RelatorioAcessoRepository relatorioAcessoRepository;

    public RelatorioAcesso registrar(RelatorioAcesso log) {
        return relatorioAcessoRepository.save(log);
    }

    public List<RelatorioAcesso> listarPorUsuario(Long usuarioId) {
        return relatorioAcessoRepository.findByUsuario_Id(usuarioId);
    }
}

