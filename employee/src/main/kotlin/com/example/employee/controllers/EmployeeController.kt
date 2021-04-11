package com.example.employee.controllers

import com.example.employee.dao.DAO
import com.example.employee.models.Employee
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController()
@RequestMapping("/employee")
class EmployeeController(val employeeDAO: DAO<Employee>) {

    @GetMapping()
    fun getAllEmployees() = employeeDAO.getAll()

    @GetMapping("/{id}")
    fun getEmployeeById(@PathVariable id: Int): Employee {
        return employeeDAO.getById(id)
    }

    @PostMapping
    fun addEmployee(@RequestBody employee: Employee): Employee {
        employeeDAO.add(employee)
        return employee
    }

    @PutMapping("/{id}")
    fun updateEmployee(@PathVariable id: Int, @RequestBody employee: Employee): Employee {
        return employeeDAO.update(id, employee)
    }

    @DeleteMapping("{id}")
    fun deleteEmployee(@PathVariable id: Int) {
        employeeDAO.delete(id)
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleException() {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

}