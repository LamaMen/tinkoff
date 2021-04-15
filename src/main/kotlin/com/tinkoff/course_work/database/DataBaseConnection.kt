package com.tinkoff.course_work.database

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DataBaseConnection {

    @Bean
    fun database(): Database {
        return Database.connect(
            "jdbc:postgresql://localhost:5432/finances?useSSL=false&amp;serverTimezone=UTC",
            driver = "org.postgresql.Driver",
            user = "ilia",
            password = "gjkbnt["
        )
    }
}