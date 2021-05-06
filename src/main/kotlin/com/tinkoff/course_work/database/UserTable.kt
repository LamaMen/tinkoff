package com.tinkoff.course_work.database

import org.jetbrains.exposed.dao.id.UUIDTable

//object UserTable : IntIdTable("user_account") {
//    val name = varchar("name", 64)
//    val password = varchar("password", 64)
//}

object UserTable : UUIDTable("user_account") {
    val name = varchar("name", 64)
    val password = varchar("password", 64)
}