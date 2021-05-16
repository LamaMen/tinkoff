package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.TransactionsTable
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import com.tinkoff.course_work.models.domain.BaseTransaction
import com.tinkoff.course_work.models.domain.Category
import com.tinkoff.course_work.models.domain.categoryId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class TransactionsDAO(private val database: Database) {
    suspend fun getTransactionById(userId: String, id: Int?, isCoast: Boolean): BaseTransaction = dbQuery {
        return@dbQuery TransactionsTable
            .select {
                if (id == null) throw TransactionNotFoundException(id)

                return@select TransactionsTable.id eq id and
                        (TransactionsTable.user eq UUID.fromString(userId)) and
                        (TransactionsTable.isCoast eq isCoast)
            }
            .map(::extractTransaction)
            .firstOrNull()
            ?: throw TransactionNotFoundException(id)
    }

    suspend fun getAllTransactionsByUser(userId: String): List<BaseTransaction> = dbQuery {
        TransactionsTable
            .select { TransactionsTable.user eq UUID.fromString(userId) }
            .map(::extractTransaction)
    }

    suspend fun addTransaction(userId: String, transaction: BaseTransaction): Int = dbQuery {
        TransactionsTable.insertAndGetId {
            setValues(it, transaction, userId)
        }.value
    }

    suspend fun updateTransaction(userId: String, transaction: BaseTransaction) {
        dbQuery {
            TransactionsTable
                .select {
                    if (transaction.id == null) throw TransactionNotFoundException(transaction.id)

                    return@select TransactionsTable.id eq transaction.id and
                            (TransactionsTable.user eq UUID.fromString(userId)) and
                            (TransactionsTable.isCoast eq transaction.isCoast)
                }
                .map(::extractTransaction)
                .firstOrNull() ?: throw TransactionNotFoundException(transaction.id)

            TransactionsTable.update({
                if (transaction.id == null) throw TransactionNotFoundException(transaction.id)

                return@update TransactionsTable.id eq transaction.id and
                        (TransactionsTable.user eq UUID.fromString(userId)) and
                        (TransactionsTable.isCoast eq transaction.isCoast)
            }) {
                setValues(it, transaction, userId)
            }
        }
    }

    suspend fun deleteTransactionById(userId: String, id: Int, isCoast: Boolean) {
        dbQuery {
            val status = TransactionsTable.deleteWhere {
                TransactionsTable.id eq id and
                        (TransactionsTable.user eq UUID.fromString(userId)) and
                        (TransactionsTable.isCoast eq isCoast)
            }
            if (status == 0) throw TransactionNotFoundException(id)
        }
    }

    private fun extractTransaction(row: ResultRow) = BaseTransaction(
        row[TransactionsTable.id].value,
        row[TransactionsTable.title],
        row[TransactionsTable.amount],
        row[TransactionsTable.isCoast],
        Category(row[TransactionsTable.category]),
        row[TransactionsTable.currency]
    )

    private fun TransactionsTable.setValues(
        it: UpdateBuilder<Int>,
        transaction: BaseTransaction,
        userId: String
    ) {
        it[amount] = transaction.amount
        it[title] = transaction.title
        it[isCoast] = transaction.isCoast
        it[user] = UUID.fromString(userId)
        it[category] = transaction.categoryId()
        it[currency] = transaction.currency
    }

    suspend fun <T> dbQuery(statement: Transaction.() -> T): T = withContext(Dispatchers.IO) {
        transaction(database, statement)
    }
}