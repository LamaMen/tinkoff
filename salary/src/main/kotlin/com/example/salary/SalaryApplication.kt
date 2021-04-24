package com.example.salary

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SalaryApplication

fun main(args: Array<String>) {
    runApplication<SalaryApplication>(*args)
}
