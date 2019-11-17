package com.examplo.configTest

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.springframework.util.ResourceUtils

class TestApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final String CUSTOM_PROPERTY_SOURCE_NAME = 'custom-test-properties'

    @Override
    void initialize(ConfigurableApplicationContext context) {
        def env = context.getEnvironment()
        expandPropertyInclude(env, 'spring.datasource.data')
    }

    /**
     * Expande uma propriedade do {@link org.springframework.core.env.Environment} cujo valor indique que os
     * verdadeiros valores são paths listados em um arquivo texto.
     *
     * @param env Environment onde está a propriedade.
     * @param key Nome da propriedade.
     */
    private static void expandPropertyInclude(ConfigurableEnvironment env, String key) {
        def value = env.getProperty(key)
        if (value?.startsWith('@')) {
            def file = ResourceUtils.getFile("classpath:${value.substring(1)}")
            def lines = file.readLines()
                    .collect { it.trim() }
                    .findAll { !it.empty && !it.startsWith('#') }
                    .collect { "classpath:$it" as String }
            def sources = env.getPropertySources()
            def source = sources.get(CUSTOM_PROPERTY_SOURCE_NAME) as MapPropertySource
            if (source == null) {
                source = new MapPropertySource(CUSTOM_PROPERTY_SOURCE_NAME, [:])
                sources.addFirst(source)
            }
            source.getSource().put(key, lines)
        }
    }
}
