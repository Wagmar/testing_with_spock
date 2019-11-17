package com.exemplo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ObjectRequest {
    @Getter @Setter
    private long cpf;
}
