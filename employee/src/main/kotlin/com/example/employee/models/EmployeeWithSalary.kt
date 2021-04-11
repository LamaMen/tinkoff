package com.example.employee.models

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Сотрудник")
class EmployeeWithSalary(
    @Schema(description = "Идентификатор сотрудника") id: Int,
    @Schema(description = "Имя сотрудника") name: String,
    @Schema(description = "Возраст сотрудника") age: Int,
    @Schema(description = "Должность сотрудника") position: String,
    @Schema(description = "Зарплата сотрудника") val salaryAmount: Long
) : Employee(id, name, age, position)
