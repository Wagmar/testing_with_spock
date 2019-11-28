package com.example.service

import com.example.configTest.ConfigTest
import org.json.JSONObject
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.test.web.servlet.result.MockMvcResultHandlers

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class ExampleCallWebServiceSpec extends ConfigTest {

    MockRestServiceServer mockServer
    private static final String URL_PUSH = "http://10.5.44.62:6011/banana"

    def '001 - deve chamar web service'(){
        given: 'Adiconando dados para o teste'

        mockServer = MockRestServiceServer.createServer(restTemplate)
        mockServer.expect(MockRestRequestMatchers.requestTo(URL_PUSH))
                .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
                .andRespond(MockRestResponseCreators.withSuccess("resultSuccess", MediaType.APPLICATION_JSON).body(
                        jsonRequest.toString()
                ))

        when: 'Executando o teste '
        def response = mockMvc.perform (RestDocumentationRequestBuilders.get("/v1/cal-web-service")
        ).andDo(MockMvcResultHandlers.print())

        then: 'validando a saida do teste'
        response.getProperties() != null

        response
                .andExpect(status().isOk())
                .andDo(document( snippetId , responseFields(
                        fieldWithPath("data.message").description("Nome"),
                        fieldWithPath("data.id").description("Idade")
                ) ))

        where: 'Os cenários do teste '
        snippetId            |  json
        "call_web_service"   |  [ message : 'Olá Mundo', id : 18 ]

         jsonRequest = new JSONObject(json)

    }

}
