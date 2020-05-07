package com.example.configTest

import com.exemplo.ExemploApplication
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.ResponseFieldsSnippet
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.web.client.RestTemplate
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields

@SpringBootTest(classes = ExemploApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ContextConfiguration(initializers = TestApplicationContextInitializer)
@TestPropertySource(locations = ['classpath:test.properties'])
@AutoConfigureRestDocs('build/generated-snippets')
@AutoConfigureMockMvc
abstract class ConfigTest extends Specification{

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    RestTemplate restTemplate;

    @Shared
    ResponseFieldsSnippet responseNotFound = responseFields(
            fieldWithPath("data.error").description("Recurso não encontrado")
    )

    def respFild(def map ) {
        List<FieldDescriptor> list = new ArrayList<>()
        map.each { key, val ->
            list.add(fieldWithPath((String)key).description(val))
        }
        ResponseFieldsSnippet responseNotFound = responseFields(list)
        return responseNotFound
    }

}
