package com.exemplo.repository;


import com.exemplo.domain.Cliente;
import com.exemplo.error.Errors;
import com.exemplo.exceptions.ApiExceptions;
import com.exemplo.exceptions.ClienteInvalido;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.slog4j.SLogger;
import org.slog4j.SLoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ClienteRepositoryImpl implements ClienteRepository {

    private static final SLogger slog = SLoggerFactory.getLogger(ClienteRepositoryImpl.class);


    private final SqlSession sqlSession;

    @Override
    public Optional<Cliente> buscar(Cliente cartao){
        return Optional.ofNullable(sqlSession.selectOne("mybatis.Cliente.buscar", cartao.getCpf()));
    }

    @Override
    public void atualizar(Cliente cliente) throws ApiExceptions {
        int update = sqlSession.update("mybatis.Cliente.atualizar", cliente);
        if (update == 0){
            throw new ClienteInvalido(Errors.CLIENTE_NAO_ENCONTRADO);
        }
    }
}
