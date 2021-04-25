package com.tinkoff.course_work

import com.tinkoff.course_work.config.DatabaseConfigurationProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DatabaseConfigurationProperties::class)
class TinkoffApplication

fun main(args: Array<String>) {
    runApplication<TinkoffApplication>(*args)
}
