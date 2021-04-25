package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.MoneyTransactionTable
import com.tinkoff.course_work.exceptions.CoastNotFoundException
import com.tinkoff.course_work.models.MoneyTransaction
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MoneyTransactionDAO(private val database: Database) {
    fun getTransactionById(id: Int, userId: Int): MoneyTransaction {
        return getCollectionFromDB(checkTransactionId(id, userId)).firstOrNull() ?: throw CoastNotFoundException()
    }

    fun getAllTransactionsByUser(userId: Int): List<MoneyTransaction> =
        getCollectionFromDB(MoneyTransactionTable.user eq userId)

    fun addTransaction(transaction: MoneyTransaction, userId: Int): Int =
        transaction(database) {
            MoneyTransactionTable.insertAndGetId {
                it[amount] = transaction.amount
                it[title] = transaction.title
                it[date] = transaction.date
                it[isCoast] = transaction.isCoast
                it[user] = userId
            }.value
        }

    fun deleteTransactionById(id: Int, userId: Int) {
        transaction(database) {
            MoneyTransactionTable.deleteWhere { checkTransactionId(id, userId) }
        }
    }

    private fun getCollectionFromDB(condition: Op<Boolean>) = transaction(database) {
        MoneyTransactionTable
            .select { condition }
            .map(::extractMoneyTransaction)
    }

    private fun checkTransactionId(transactionId: Int, userId: Int) =
        MoneyTransactionTable.id eq transactionId and (MoneyTransactionTable.user eq userId)

    private fun extractMoneyTransaction(row: ResultRow) = MoneyTransaction(
        row[MoneyTransactionTable.id].value,
        row[MoneyTransactionTable.title],
        row[MoneyTransactionTable.amount],
        row[MoneyTransactionTable.date],
        row[MoneyTransactionTable.isCoast]
    )
}
