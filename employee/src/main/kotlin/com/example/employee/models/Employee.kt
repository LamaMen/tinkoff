package com.example.employee.models

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Сотрудник")
open class Employee(
    @Schema(description = "Идентификатор сотрудника") val id: Int,
    @Schema(description = "Имя сотрудника") val name: String,
    @Schema(description = "Возраст сотрудника") val age: Int,
    @Schema(description = "Должность сотрудника") val position: String
)
