package com.example.employee.dao

interface DAO<T> {
    fun getAll(): List<T>

    fun getById(id: Int): T

    fun add(element: T)

    fun update(id: Int, element: T): T

    fun delete(id: Int)
}