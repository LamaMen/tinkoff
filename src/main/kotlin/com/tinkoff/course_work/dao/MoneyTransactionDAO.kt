package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.MoneyTransactionTable
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.MoneyTransaction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class MoneyTransactionDAO(private val database: Database) {
    suspend fun getTransactionById(id: Int?, userId: Int): MoneyTransaction {
        return getCollectionFromDB(checkTransactionId(id, userId)).firstOrNull() ?: throw TransactionNotFoundException()
    }

    suspend fun getAllTransactionsByUser(userId: Int): List<MoneyTransaction> =
        getCollectionFromDB(MoneyTransactionTable.user eq userId)

    suspend fun addTransaction(transaction: MoneyTransaction, userId: Int): Int = dbQuery {
        MoneyTransactionTable.insertAndGetId {
            setValues(it, transaction, userId)
        }.value
    }

    suspend fun updateTransaction(transaction: MoneyTransaction, userId: Int) = dbQuery {
        MoneyTransactionTable.update({ checkTransactionId(transaction.id, userId) }) {
            setValues(it, transaction, userId)
        }
    }

    suspend fun deleteTransactionById(id: Int, userId: Int) = dbQuery {
        MoneyTransactionTable.deleteWhere { checkTransactionId(id, userId) }
    }

    private suspend fun getCollectionFromDB(condition: Op<Boolean>) = dbQuery {
        MoneyTransactionTable
            .select { condition }
            .map(::extractMoneyTransaction)
    }

    private fun checkTransactionId(transactionId: Int?, userId: Int): Op<Boolean> {
        if (transactionId == null) {
            throw TransactionNotFoundException()
        } else {
            return MoneyTransactionTable.id eq transactionId and (MoneyTransactionTable.user eq userId)
        }
    }

    private fun extractMoneyTransaction(row: ResultRow) = MoneyTransaction(
        row[MoneyTransactionTable.id].value,
        row[MoneyTransactionTable.title],
        row[MoneyTransactionTable.amount],
        row[MoneyTransactionTable.date],
        row[MoneyTransactionTable.isCoast]
    )

    private fun MoneyTransactionTable.setValues(
        it: UpdateBuilder<Int>,
        transaction: MoneyTransaction,
        userId: Int
    ) {
        it[amount] = transaction.amount
        it[title] = transaction.title
        it[date] = transaction.date
        it[isCoast] = transaction.isCoast
        it[user] = userId
    }

    suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction(database) { block() }
    }
}

