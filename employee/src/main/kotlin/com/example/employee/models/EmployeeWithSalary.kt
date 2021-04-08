package com.example.employee.models

class EmployeeWithSalary(
    id: Int,
    name: String,
    age: Int,
    position: String,
    val salaryAmount: Long
) : Employee(id, name, age, position)
