package com.example.salary.dao

import com.example.salary.models.Salary
import org.springframework.stereotype.Repository

@Repository
class SalaryDAO {
    private val salaries = mutableListOf(
        Salary(1, 100L),
        Salary(2, 500L),
        Salary(3, 10L),
        Salary(4, 1L)
    )

    fun getSalaryByEmployeeId(id: Int): Salary {
        return salaries.find { it.employeeId == id } ?: createSalary(id)
    }

    private fun createSalary(id: Int): Salary {
        val salary = Salary(id, 1L)
        salaries.add(salary)
        return salary
    }
}