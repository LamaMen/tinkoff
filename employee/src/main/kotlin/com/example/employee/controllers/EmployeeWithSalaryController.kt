package com.example.employee.controllers

import com.example.employee.integration.EmployeeIntegrationComponent
import com.example.employee.models.EmployeeWithSalary
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employee_with_salary")
class EmployeeWithSalaryController(val employeeIntegrationComponent: EmployeeIntegrationComponent) {

    @GetMapping("/{id}")
    fun getEmployee(@PathVariable id: Int): EmployeeWithSalary {
        return employeeIntegrationComponent.getEmployeeWithSalary(id)
    }
}