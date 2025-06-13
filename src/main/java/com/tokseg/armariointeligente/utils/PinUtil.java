package com.tokseg.armariointeligente.utils;

import java.security.SecureRandom;

public class PinUtil {

    private static final SecureRandom random = new SecureRandom();

    /**
     * Gera um PIN numérico aleatório de 6 dígitos.
     * @return String contendo o PIN de 6 dígitos.
     */
    public static String gerarPin6Digitos() {
        int pin = 100000 + random.nextInt(900000); // Gera número entre 100000 e 999999
        return String.valueOf(pin);
    }
}