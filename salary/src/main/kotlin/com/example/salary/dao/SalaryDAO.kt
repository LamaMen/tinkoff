package com.example.salary.dao

import com.example.salary.db.SalaryTable
import com.example.salary.models.Salary
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class SalaryDAO(val database: Database) {
    fun getSalaryByEmployeeId(employeeId: Int): Salary {
        val salaryFromDb = transaction(database) {
            SalaryTable
                .select { SalaryTable.employeeId eq employeeId }
                .map(::extractSalary)
                .firstOrNull()
        }
        return salaryFromDb ?: createSalary(employeeId)
    }

    private fun extractSalary(row: ResultRow) = Salary(
        row[SalaryTable.employeeId],
        row[SalaryTable.amount]
    )

    private fun createSalary(id: Int): Salary {
        val salary = Salary(id, 1L)
        transaction(database) {
            SalaryTable.insert {
                it[amount] = salary.amount
                it[employeeId] = salary.employeeId
            }
        }
        return salary
    }
}