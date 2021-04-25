package com.tinkoff.course_work.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "database")
data class DatabaseConfigurationProperties(
    val url: String,
    val user: String,
    val password: String
)