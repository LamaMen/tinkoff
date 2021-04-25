package com.tinkoff.course_work.database

import org.jetbrains.exposed.sql.Database
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataBaseConnection {
    @Value("\${database.url}")
    private lateinit var databaseUrl: String

    @Value("\${database.user}")
    private lateinit var databaseUser: String

    @Value("\${database.password}")
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