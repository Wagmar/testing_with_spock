package com.exemplo.error;

public interface Error {

    /**
     * @return Código de erro global que será usado para identificar de maneira inequívoca o erro ocorrido.
     */
    int code();

    /**
     * @return Texto descritivo do erro.
     */
    String message();
}
