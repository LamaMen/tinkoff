package com.tinkoff.course_work.models

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("user_account") {
    val login = varchar("login", 255).uniqueIndex()
    val name = varchar("name", 255)
    val password = varchar("password", 255)
}