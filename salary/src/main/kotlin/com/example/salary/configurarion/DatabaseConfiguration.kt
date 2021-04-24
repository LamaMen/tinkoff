package com.example.salary.configurarion

import org.jetbrains.exposed.sql.Database
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

@Configuration
class DatabaseConfiguration(env: Environment) {
    val dbUrl = env.getProperty("spring.flyway.url", "jdbc:postgresql://localhost:5432/employee")
    val dbUser = env.getProperty("spring.flyway.user", "root")
    val dbPassword = env.getProperty("spring.flyway.password", "root")


    @Bean
    fun database(): Database {
        return Database.connect(
            dbUrl,
            driver = "com.mysql.cj.jdbc.Driver",
            user = dbUser,
            password = dbPassword
        )
    }
}