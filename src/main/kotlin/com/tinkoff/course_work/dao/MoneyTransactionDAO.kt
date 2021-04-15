package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.MoneyTransactionTable
import com.tinkoff.course_work.models.MoneyTransaction
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MoneyTransactionDAO(val database: Database) {
    fun getTransactionById(id: Int): MoneyTransaction =
        transaction(database) {
            MoneyTransactionTable
                .select { MoneyTransactionTable.id eq id }
                .limit(1).single()
                .let(::extractMoneyTransaction)
        }


    fun getAllTransactionsByUser(): List<MoneyTransaction> = transaction(database) {
        MoneyTransactionTable.selectAll().map(::extractMoneyTransaction)
    }

    private fun extractMoneyTransaction(row: ResultRow) = MoneyTransaction(
        row[MoneyTransactionTable.id].value,
        row[MoneyTransactionTable.amount],
        row[MoneyTransactionTable.title],
        row[MoneyTransactionTable.date],
        row[MoneyTransactionTable.isCoast]
    )
}
