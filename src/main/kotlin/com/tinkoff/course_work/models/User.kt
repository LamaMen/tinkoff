package com.tinkoff.course_work.models

data class User(
    val id: Int?,
    val login: String,
    val name: String,
    val password: String
)