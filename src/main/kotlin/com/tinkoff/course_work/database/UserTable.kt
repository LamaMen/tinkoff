package com.tinkoff.course_work.database

import org.jetbrains.exposed.dao.id.IntIdTable

object UserTable : IntIdTable("user_account") {
    val name = varchar("name", 64)

    //    val login = varchar("login", 255).uniqueIndex()
    val password = varchar("password", 64)
}