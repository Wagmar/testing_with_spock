package com.examplo.service

import com.examplo.configTest.ConfigTest
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Unroll

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.payload.PayloadDocumentation.*

class ExampleSpec extends ConfigTest {

    def setupSpec(){
        println()
        println("setup executado apenas uma vez no inicio da classe do teste ")
        println()
    }
//
//    def setup(){
//        println("setup antes de cada metodo")
//        println()
//    }
//
//    def cleanup(){
//        println("setup no final de cada metodo")
//        println()
//    }
//
//    def cleanupSpec(){
//        println()
//        println("setup executado apenas uma vez no final da classe do teste ")
//        println()
//    }

    @Unroll
    def '001 - Buscar Cliente com o cpf: #cpf '(){

        given:
        def requestFieldss = requestFields(
                fieldWithPath("cpf").description("Numero CPF do Cliente")
        )
        def responseFieldss = responseFields(
                fieldWithPath("data.cpf").description("Numero CPF cartão"),
                fieldWithPath("data.nome").description("Nome do Cliente")
        )
        def json = [ "cpf" : cpf ]
        def request = new JSONObject(json)
        when:
        def response = mockMvc.perform (RestDocumentationRequestBuilders.post("/v1/cliente/busca")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(request.toString())
        ).andDo(MockMvcResultHandlers.print())

        then:
        if(cartaoOK){
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFieldss , responseFieldss ))
        } else {
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFieldss , responseNotFound))
        }

        where:
        snippetId               | cpf             | status    | cartaoOK
        'consulta-cpf-valido'   | 11980904881     | 200       | true
        'consulta-cpf-invalido' | 9980904881      | 404       | false

    }

    @Unroll
    def '002 - atualizar cliente'(){
        given:
        def json = [ "cpf" : cpf , "nome": nome ]
        def request = new JSONObject(json)
        def         requestFieldss = requestFields(
                fieldWithPath("cpf").description("Numero do CPF cartão"),
                fieldWithPath("nome").description("Nome do Cliente")
        )

        when:
        def response = mockMvc.perform (RestDocumentationRequestBuilders.put("/v1/cliente/atualiza")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(request.toString())
        ).andDo(MockMvcResultHandlers.print())

        then:
        if(OK){
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFieldss, responseFields(
                            fieldWithPath("code").description("Código de retorno")) ))

            and:
            jdbcTemplate.queryForObject("""SELECT nm_cliente FROM wagmar.cliente where nr_cpf = $cpf""", String.class) == nome

        } else {
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFieldss , responseNotFound))
        }

        where:
        snippetId                              | cpf          | nome                 | status  | OK
        'atualizar-cliente'                    | 11980904881  | 'João Pedro'         | 200     | true
        'atualizar-cliente-cpf-nao-encontrado' | 9980904881   | "Maria do Rosário"   | 404     | false

    }

}
