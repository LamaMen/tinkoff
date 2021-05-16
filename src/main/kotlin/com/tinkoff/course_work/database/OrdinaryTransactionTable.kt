package com.tinkoff.course_work.database

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.`java-time`.datetime

object OrdinaryTransactionTable : Table("ordinary_transaction") {
    val id = reference("id", TransactionsTable)
    val date = datetime("date")
    override val primaryKey = PrimaryKey(id)
}