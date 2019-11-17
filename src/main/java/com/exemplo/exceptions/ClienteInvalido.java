package com.exemplo.exceptions;

import com.exemplo.error.Error;

public class ClienteInvalido extends ApiExceptions {
    public ClienteInvalido(Error error) {
        super(error);
    }
}
