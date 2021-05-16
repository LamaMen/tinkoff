package com.tinkoff.course_work.models.json


interface BasicJson {
    val id: Int?
    val title: String
    val amount: Long
    val category: String?
    val currency: String?

    fun validateCategory(): Boolean
}