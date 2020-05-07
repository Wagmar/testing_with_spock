package com.exemplo.repository;

import com.exemplo.domain.Cliente;
import com.exemplo.exceptions.ApiExceptions;

import java.util.Optional;

public interface ClienteRepository {
    Optional<Cliente> buscar(Cliente cartao);

    void atualizar(Cliente cliente) throws ApiExceptions;
}
