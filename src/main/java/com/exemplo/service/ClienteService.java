package com.exemplo.service;


import com.exemplo.domain.Cliente;
import com.exemplo.exceptions.ApiExceptions;

public interface ClienteService {

    Cliente buscar(Cliente cartao) throws ApiExceptions;

    void atualizar(Cliente cliente) throws ApiExceptions;
}
