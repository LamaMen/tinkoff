package com.tinkoff.course_work.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class MainConfig {
    @Bean
    fun webClient(@Value("\${api.url}") apiUrl: String): WebClient {
        return WebClient.create(apiUrl)
    }
}