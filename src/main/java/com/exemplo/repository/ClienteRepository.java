package com.exemplo.repository;

import com.exemplo.domain.Cliente;
import com.exemplo.exceptions.ApiExceptions;

public interface ClienteRepository {
    Cliente buscar(Cliente cartao);

    void atualizar(Cliente cliente) throws ApiExceptions;
}
