package com.tinkoff.course_work.database

import org.jetbrains.exposed.dao.id.IntIdTable

object CategoryTable : IntIdTable("category") {
    val name = varchar("name", 64)
}