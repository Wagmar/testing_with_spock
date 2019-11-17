package com.exemplo.service

import com.exemplo.domain.Cliente
import com.exemplo.exceptions.ClienteInvalido
import com.exemplo.repository.ClienteRepository
import com.exemplo.repository.ClienteRepositoryImpl
import org.apache.ibatis.session.SqlSession
import spock.lang.Specification
import spock.lang.Unroll

class ClienteServiceImplSpec extends Specification {

    @Unroll
    def "test buscar cliente: #cliente"() {
        given:
        def repository = Mock(ClienteRepository){
            buscar(_) >> Cliente.builder().cpf(111111111l).nome('Corey Taylor').build()
        }
        def service = new ClienteServiceImpl(repository)

        when:
         def corey = service.buscar(cliente)
        then:
         corey != null

        where:
        cliente | tt
        Cliente.builder().cpf(111111111).build() | _
    }

    def "test atualizar"() {
        given:
        def cliente = Cliente.builder().cpf(111111111).build()
        def sqlSession = Mock(SqlSession){
            update(_, _) >> 0
        }

        def repository = new ClienteRepositoryImpl(sqlSession)
        def service = new ClienteServiceImpl(repository)

        when:
        service.atualizar(cliente)

        then:
        def e = thrown(ClienteInvalido)
        and: 'Validando a messagem da exceçao'
        e.getMessage() == 'Cliente não encontrado.'
        0 * sqlSession.update(1,_)
    }
}
