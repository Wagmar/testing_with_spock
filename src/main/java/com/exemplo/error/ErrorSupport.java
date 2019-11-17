package com.exemplo.error;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorSupport {
    /**
     * Substitui mensagem associada ao erro por outra, mais simples, mas com código acrescentado ao final do texto.
     *
     * @param error Erro original.
     * @param newMessage Nova mensagem simplificada.
     * @return Nova mensagem simplificada.
     */
    public static String rewordMessage(Error error, String newMessage) {
        return String.format("%s #%d", newMessage, error.code());
    }
}
