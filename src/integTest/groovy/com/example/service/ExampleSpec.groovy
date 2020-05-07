package com.example.service

import com.example.configTest.ConfigTest
import org.json.JSONObject
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Shared
import spock.lang.Unroll

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.payload.PayloadDocumentation.*

class ExampleSpec extends ConfigTest {

    @Shared sql = jdbcTemplate

    def setupSpec(){
        println()
        println("sql está null: "+sql)
        println()
    }

    def setup(){
        println("setup antes de cada metodo")
        jdbcTemplate.execute("""UPDATE wagmar.cliente set nm_cliente = 'teste' where nr_cpf = 11980904881""")
        println()
    }

    def cleanup(){
        println("setup no final de cada metodo")
        println()
    }

    def cleanupSpec(){
        println()
        println("setup executado apenas uma vez no final da classe do teste ")
        println()
    }

    @Unroll
    def '001 - Buscar Cliente com o cpf: #cpf '(){

        given:
        def requestFields = requestFields(
                fieldWithPath("cpf").description("Numero CPF do Cliente")
        )
        def responseFields = responseFields(
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
        if(requestOK){
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFields , responseFields ))
        } else {
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFields , responseBody))
        }

        where:
        snippetId               | cpf             | status    | requestOK | responseBody
        'consulta-cpf-valido'   | 11980904881     | 200       | true      | _
        'consulta-cpf-invalido' | 9980904881      | 404       | false     |  respFild(["code": "1002", "message":"Cliente não encontrado"])
        'consulta-cpf-null'     | null            | 400       | false     |  respFild(["code": "Codigo de erro da requisição", "message":"Mensagem da requisição", "errors": "Erros", "errors[0].message":"Mensagem", "errors[0].fieldName":"Parâmetro"])
    }

    @Unroll
    def '002 - atualizar cliente'(){
        given:
        def json = [ "cpf" : cpf , "nome": nome ]
        def request = new JSONObject(json)
        def         requestFields = requestFields(
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
                    .andDo(document( snippetId ,  requestFields, responseFields(
                            fieldWithPath("code").description("Código de retorno")) ))

            and:
            jdbcTemplate.queryForObject("""SELECT nm_cliente FROM wagmar.cliente where nr_cpf = $cpf""", String.class) == nome
        } else {
            response
                    .andExpect(MockMvcResultMatchers.status().is(status))
                    .andDo(document( snippetId ,  requestFields , responseBody))
        }

        where:
        snippetId                              | cpf          | nome                 | status  | OK    | responseBody
        'atualizar-cliente'                    | 11980904881  | 'João Pedro'         | 200     | true  | _
        'atualizar-cliente-cpf-nao-encontrado' | 9980904881   | "Maria do Rosário"   | 404     | false | respFild("code" : "Codigo de erro da requisição", "message":"Mensagem da requisição")
    }
}