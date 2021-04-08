package com.example.employee.integration

import com.example.employee.dao.DAO
import com.example.employee.models.Employee
import com.example.employee.models.EmployeeWithSalary
import com.example.employee.models.Salary
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class EmployeeIntegrationComponent(val employeesDao: DAO<Employee>) {
    private val restTemplate: RestTemplate = RestTemplate()

    fun getEmployeeWithSalary(id: Int): EmployeeWithSalary {
        val salary =
            restTemplate.getForObject("http://localhost:9090/$id", Salary::class.java) ?: throw NoSuchElementException()
        val employee = employeesDao.getById(id)
        return createEmployeeWithSalary(employee, salary)
    }

    private fun createEmployeeWithSalary(employee: Employee, salary: Salary): EmployeeWithSalary {
        return EmployeeWithSalary(employee.id, employee.name, employee.age, employee.position, salary.amount)
    }
}