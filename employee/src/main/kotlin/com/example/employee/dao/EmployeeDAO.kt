package com.example.employee.dao

import com.example.employee.models.Employee
import org.springframework.stereotype.Repository

@Repository
class EmployeeDAO : DAO<Employee> {
    private val employees = mutableListOf(
        Employee(1,"Alex", 25, "Engineer"),
        Employee(2,"Ivan", 56, "Director"),
        Employee(3, "Kate", 31, "Engineer"),
        Employee(4, "Bred", 29, "Worker"),
    )

    override fun getAll(): List<Employee> = employees

    override fun getById(id: Int): Employee = employees.find { compareById(id, it) } ?: throw NoSuchElementException()

    override fun add(element: Employee) {
        employees.add(element)
    }

    override fun update(id: Int, element: Employee): Employee {
        val indexOfEmployee = employees.indexOfFirst { compareById(id, it) }
        if (indexOfEmployee == -1) throw NoSuchElementException("Работника с таким id не существует.")

        employees[indexOfEmployee] = element
        return element
    }

    override fun delete(id: Int) {
        employees.removeIf { compareById(id, it) }
    }

    private fun compareById(id: Int, employee: Employee) = employee.id == id
}