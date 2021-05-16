package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.OrdinaryTransactionTable
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class OrdinaryDAO(private val database: Database) {
    suspend fun getDateByTransactionId(id: Int?): LocalDateTime {
        return dbQuery {
            OrdinaryTransactionTable
                .select { OrdinaryTransactionTable.id eq id }
                .map(::extractDate)
                .firstOrNull()?.second ?: throw TransactionNotFoundException(id)
        }
    }

    suspend fun saveDate(pair: Pair<Int, LocalDateTime>) = dbQuery {
        OrdinaryTransactionTable.insert {
            setValues(it, pair)
        }
    }

    suspend fun deleteTransactionById(id: Int) {
        dbQuery {
            val status = OrdinaryTransactionTable.deleteWhere { OrdinaryTransactionTable.id eq id }
            if (status == 0) throw TransactionNotFoundException(id)
        }
    }

    private fun extractDate(row: ResultRow): Pair<Int, LocalDateTime> =
        row[OrdinaryTransactionTable.id].value to row[OrdinaryTransactionTable.date]

    private fun OrdinaryTransactionTable.setValues(
        it: UpdateBuilder<Int>,
        pair: Pair<Int, LocalDateTime>,
    ) {
        it[id] = pair.first
        it[date] = pair.second
    }

    suspend fun <T> dbQuery(statement: Transaction.() -> T): T = withContext(Dispatchers.IO) {
        transaction(database, statement)
    }
}