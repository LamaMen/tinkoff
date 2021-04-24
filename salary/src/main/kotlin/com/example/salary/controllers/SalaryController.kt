package com.example.salary.controllers

import com.example.salary.dao.SalaryDAO
import com.example.salary.models.Salary
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class SalaryController(val salaries: SalaryDAO) {

    @GetMapping("/{id}")
    fun getSalary(@PathVariable id: Int): Salary {
        return salaries.getSalaryByEmployeeId(id)
    }
}