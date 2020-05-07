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
        given: 'criando cliente para teste'
        def repository = Mock(ClienteRepository){
            buscar(_) >> Optional.ofNullable(cliente)
        }
        def service = new ClienteServiceImpl(repository)

        when: 'buscando o cliente'
         def corey = service.buscar(cliente)
        then: 'verificação do cliente'
         corey != null
        and: 'verificação do cpf'
         corey.cpf == 111111111l
        and: 'verificação do nome'
         corey.nome == 'Corey Taylor'

        where: 'Onde cliente'
        cliente                    | tt
        getCliente('Corey Taylor') | _
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

    def getCliente(String name){
        def cliente  = new Cliente();
        cliente.setCpf(111111111)
        cliente.setNome(name)
        return  cliente
    }
}
