package com.exemplo.error;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Accessors(fluent = true)
public enum  Errors implements Error {

    WEB_SERVICE_NAO_RESPONDE(1001, "Webservce não responde" ),
    CLIENTE_NAO_ENCONTRADO(1002,"Cliente não encontrado.");



    private final int code;
    private final String message;
}
