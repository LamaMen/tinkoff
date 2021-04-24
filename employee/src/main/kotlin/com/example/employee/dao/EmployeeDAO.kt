package com.example.employee.dao

import com.example.employee.db.EmployeeTable
import com.example.employee.models.Employee
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class EmployeeDAO(val database: Database) {
    fun getById(id: Int): Employee = transaction(database) {
        EmployeeTable
            .select { EmployeeTable.id eq id }
            .limit(1).single()
            .let(::extractEmployee)
    }

    private fun extractEmployee(row: ResultRow) = Employee(
        row[EmployeeTable.id].value,
        row[EmployeeTable.name],
        row[EmployeeTable.age],
        row[EmployeeTable.position]
    )

//    fun add(element: Employee) {
//        employees.add(element)
//    }

    private fun compareById(id: Int, employee: Employee) = employee.id == id
}