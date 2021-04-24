package com.example.employee.db

import org.jetbrains.exposed.dao.id.IntIdTable

object EmployeeTable : IntIdTable(name = "employee_info") {
    val name = varchar("name", 50)
    val age = integer("age")
    val position = varchar("position", 50)
}