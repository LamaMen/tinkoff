package com.example.employee.integration

import com.example.employee.dao.EmployeeDAO
import com.example.employee.exception.NoEmployeeException
import com.example.employee.models.Employee
import com.example.employee.models.EmployeeWithSalary
import com.example.employee.models.Salary
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class EmployeeIntegrationComponent(val employeesDao: EmployeeDAO, env: Environment) {
    private val salaryUrl = env.getProperty("service.salary.url", "http://localhost:9090/")
    private val restTemplate: RestTemplate = RestTemplate()

    fun getEmployeeWithSalary(id: Int): EmployeeWithSalary {
        val salary =
            restTemplate.getForObject("$salaryUrl$id", Salary::class.java) ?: throw NoEmployeeException()
        val employee = employeesDao.getById(id)
        return createEmployeeWithSalary(employee, salary)
    }

    private fun createEmployeeWithSalary(employee: Employee, salary: Salary): EmployeeWithSalary {
        return EmployeeWithSalary(employee.id, employee.name, employee.age, employee.position, salary.amount)
    }
}