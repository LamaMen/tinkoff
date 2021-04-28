package com.tinkoff.course_work.database

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataBaseConnection {
    @Value("\${spring.datasource.url}")
    private lateinit var databaseUrl: String

    @Value("\${spring.datasource.username}")
    private lateinit var databaseUser: String

    @Value("\${spring.datasource.password}")
    private lateinit var databasePassword: String


    @Bean
    fun database(): Database {
        return Database.connect(
            databaseUrl,
            driver = "org.postgresql.Driver",
            user = databaseUser,
            password = databasePassword
        )
    }
}