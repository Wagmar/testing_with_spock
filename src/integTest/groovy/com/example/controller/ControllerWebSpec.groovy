package com.example.controller

import com.example.configTest.ConfigTest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Unroll

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document


class ControllerWebSpec extends ConfigTest {


    @Unroll
    def '001 - deve chamar Web Controller passando #usuario e retornar na index '(){

        given:
        def destino = 'index'

        when:
        def response = mockMvc.perform (RestDocumentationRequestBuilders.get("/index")
                .param("usuario", usuario)).andDo(MockMvcResultHandlers.print())

        then:
           response.andExpect(MockMvcResultMatchers.status().is(200))
                   .andExpect(MockMvcResultMatchers.forwardedUrl())
        .andExpect(MockMvcResultMatchers.request().attribute("usuario", usuario))
                   .andDo(document( "index"))

        and:
        response.andReturn().getModelAndView().getModel() == ['usuario': usuario]
        response.andReturn().getModelAndView().getViewName() == destino

        where:
        snippetId                   | usuario
        'consulta-usuario-valido'   | 'Soujava'
    }
}