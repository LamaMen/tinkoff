package com.example.employee.controllers

import com.example.employee.integration.EmployeeIntegrationComponent
import com.example.employee.models.EmployeeWithSalary
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/employee_with_salary")
class EmployeeWithSalaryController(val employeeIntegrationComponent: EmployeeIntegrationComponent) {

    @GetMapping("/{id}")
    @Operation(summary = "Возращает сотрудника по id, вместе с данными о его зарплате")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Все хорошо"),
            ApiResponse(responseCode = "404", description = "Сотрудника с таким id нет")
        ]
    )
    fun getEmployee(@PathVariable @Parameter(name = "Идентифтор сотрудника") id: Int): EmployeeWithSalary {
        return employeeIntegrationComponent.getEmployeeWithSalary(id)
    }
}