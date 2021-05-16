package com.tinkoff.course_work.database

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.datetime

object MoneyTransactionTable : IntIdTable("money_transaction") {
    val amount = long("amount")
    val title = varchar("title", 255)
    val date = datetime("date")
    val isCoast = bool("is_coast")
    val user = reference("user_id", UserTable)
    val category = integer("category_id")
    val currency = varchar("currency", 3)
}