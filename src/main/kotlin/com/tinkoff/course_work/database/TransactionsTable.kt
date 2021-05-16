package com.tinkoff.course_work.database

import org.jetbrains.exposed.dao.id.IntIdTable

object TransactionsTable : IntIdTable("transactions") {
    val amount = long("amount")
    val title = varchar("title", 255)
    val isCoast = bool("is_coast")
    val user = reference("user_id", UserTable)
    val category = integer("category_id")
    val currency = varchar("currency", 3)
}