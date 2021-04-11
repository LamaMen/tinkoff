package com.example.employee.controllers

import com.example.employee.dao.DAO
import com.example.employee.models.Employee
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.*

@RestController()
@RequestMapping("/employee")
class EmployeeController(val employeeDAO: DAO<Employee>) {

    @GetMapping()
    @Operation(summary = "Возращает список всех сотрудников")
    @ApiResponse(responseCode = "200", description = "Все хорошо")
    fun getAllEmployees() = employeeDAO.getAll()

    @GetMapping("/{id}")
    @Operation(summary = "Возращает сотрудника по id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Все хорошо"),
            ApiResponse(responseCode = "404", description = "Сотрудника с таким id нет")
        ]
    )
    fun getEmployeeById(@PathVariable @Parameter(name = "Идентифтор сотрудника") id: Int): Employee {
        return employeeDAO.getById(id)
    }

    @PostMapping
    @Operation(summary = "Добавляет нового сотрудника")
    @ApiResponse(responseCode = "200", description = "Сотрудник добавлен успешно")
    fun addEmployee(@RequestBody @Parameter(name = "Сотрудник, который будет добавлен") employee: Employee): Employee {
        employeeDAO.add(employee)
        return employee
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновляет данные сотрудника по id")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Данные успешно обновлены"),
            ApiResponse(responseCode = "404", description = "Сотрудника с таким id нет")
        ]
    )
    fun updateEmployee(
        @PathVariable @Parameter(name = "Идентифтор сотрудника, данные которого надо обновить") id: Int,
        @RequestBody @Parameter(name = "Данные которые будут установлены") employee: Employee
    ): Employee {
        return employeeDAO.update(id, employee)
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Удаляет сотрудника по его id")
    @ApiResponse(responseCode = "200", description = "Сотрудник успешно удалён")
    fun deleteEmployee(@PathVariable @Parameter(name = "Идентифтор сотрудника, данные которого будут удалены") id: Int) {
        employeeDAO.delete(id)
    }
}