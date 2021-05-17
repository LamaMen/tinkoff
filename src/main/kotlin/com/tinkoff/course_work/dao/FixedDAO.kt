package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.FixedTable
import com.tinkoff.course_work.exceptions.TransactionNotFoundException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.UpdateBuilder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class FixedDAO(private val database: Database) {
    suspend fun getDayByTransactionId(id: Int?): Int? {
        return dbQuery {
            FixedTable
                .select { FixedTable.id eq id }
                .map(::extractDay)
                .firstOrNull()?.second
        }
    }

    suspend fun saveDay(pair: Pair<Int, Int>) = dbQuery {
        FixedTable.insert {
            setValues(it, pair)
        }
    }

    suspend fun deleteTransactionById(id: Int) {
        dbQuery {
            val status = FixedTable.deleteWhere { FixedTable.id eq id }
            if (status == 0) throw TransactionNotFoundException(id)
        }
    }

    private fun extractDay(row: ResultRow): Pair<Int, Int> =
        row[FixedTable.id].value to row[FixedTable.day]

    private fun FixedTable.setValues(
        it: UpdateBuilder<Int>,
        pair: Pair<Int, Int>,
    ) {
        it[id] = pair.first
        it[day] = pair.second
    }

    suspend fun <T> dbQuery(statement: Transaction.() -> T): T = withContext(Dispatchers.IO) {
        transaction(database, statement)
    }
}