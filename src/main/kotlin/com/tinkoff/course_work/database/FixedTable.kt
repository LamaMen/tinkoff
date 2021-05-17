package com.tinkoff.course_work.database

import org.jetbrains.exposed.sql.Table

object FixedTable : Table("fixed_transaction") {
    val id = reference("id", TransactionsTable)
    val day = integer("day")
    override val primaryKey = PrimaryKey(id)
}