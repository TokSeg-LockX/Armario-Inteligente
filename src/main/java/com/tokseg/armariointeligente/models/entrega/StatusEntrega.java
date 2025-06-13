package com.tokseg.armariointeligente.models.entrega;

public enum StatusEntrega {
    AGUARDANDO_DEPOSITO, // Entregador solicitou, PIN gerado, aguardando item ser colocado
    DEPOSITO_PENDENTE_CONFIRMACAO, // Item colocado, aguardando confirmação do entregador (se necessário)
    PENDENTE,            // Item depositado e confirmado, aguardando retirada pelo destinatário (PIN do destinatário gerado)
    RETIRADO,
    EXPIRADO,            // Entrega expirou (seja por não depósito ou não retirada)
    CANCELADO
}

