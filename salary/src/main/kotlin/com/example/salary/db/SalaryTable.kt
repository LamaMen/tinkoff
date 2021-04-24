package com.example.salary.db

import org.jetbrains.exposed.dao.id.IntIdTable

object SalaryTable : IntIdTable("salary") {
    val employeeId = integer("employee_id")
    val amount = long("amount")
}