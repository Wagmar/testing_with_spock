package com.exemplo.service;

import com.exemplo.domain.Cliente;
import com.exemplo.error.Errors;
import com.exemplo.exceptions.ApiExceptions;
import com.exemplo.exceptions.ClienteInvalido;
import com.exemplo.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.slog4j.SLogger;
import org.slog4j.SLoggerFactory;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ClienteServiceImpl implements ClienteService {

    private static final SLogger slog = SLoggerFactory.getLogger(ClienteServiceImpl.class);

    private final ClienteRepository clienteRepository;

    @Override
    public Cliente buscar(Cliente cliente) throws ApiExceptions {

        var clienteRecuperado = clienteRepository.buscar(cliente);
        if (clienteRecuperado == null){
            throw new ClienteInvalido(Errors.CLIENTE_NAO_ENCONTRADO);
        }
        return clienteRecuperado;
    }

    @Override
    public void atualizar(Cliente cliente) throws ApiExceptions {
        clienteRepository.atualizar(cliente);
    }
}
