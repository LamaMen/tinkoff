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
import java.util.*

@Repository
class MoneyTransactionDAO(private val database: Database) {
    suspend fun getTransactionById(id: Int?, isCoast: Boolean, userId: String): MoneyTransaction {
        return getCollectionFromDB(checkTransactionId(id, isCoast, userId)).firstOrNull()
            ?: throw TransactionNotFoundException()
    }

    suspend fun getAllTransactionsByUser(userId: String): List<MoneyTransaction> =
        getCollectionFromDB(MoneyTransactionTable.user eq UUID.fromString(userId))

    suspend fun addTransaction(transaction: MoneyTransaction, userId: String): Int = dbQuery {
        MoneyTransactionTable.insertAndGetId {
            setValues(it, transaction, userId)
        }.value
    }

    suspend fun updateTransaction(transaction: MoneyTransaction, userId: String) =
        dbQuery {
            MoneyTransactionTable.update({ checkTransactionId(transaction.id, transaction.isCoast, userId) }) {
                setValues(it, transaction, userId)
            }
        }

    suspend fun deleteTransactionById(id: Int, isCoast: Boolean, userId: String) =
        dbQuery {
            MoneyTransactionTable.deleteWhere { checkTransactionId(id, isCoast, userId) }
        }

    private suspend fun getCollectionFromDB(condition: Op<Boolean>) = dbQuery {
        MoneyTransactionTable
            .select { condition }
            .map(::extractMoneyTransaction)
    }

    private fun checkTransactionId(transactionId: Int?, isCoast: Boolean, userId: String): Op<Boolean> {
        if (transactionId == null) {
            throw TransactionNotFoundException()
        } else {
            return MoneyTransactionTable.id eq transactionId and
                    (MoneyTransactionTable.user eq UUID.fromString(userId)) and
                    (MoneyTransactionTable.isCoast eq isCoast)
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
        userId: String
    ) {
        it[amount] = transaction.amount
        it[title] = transaction.title
        it[date] = transaction.date
        it[isCoast] = transaction.isCoast
        it[user] = UUID.fromString(userId)
    }

    suspend fun <T> dbQuery(statement: Transaction.() -> T): T = withContext(Dispatchers.IO) {
        transaction(database, statement)
    }
}

