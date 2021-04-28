package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.UserTable
import com.tinkoff.course_work.exceptions.NoSuchUserException
import com.tinkoff.course_work.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class UserDAO(private val database: Database) {
    suspend fun findByUsername(name: String): User =
        getUserCollectionFromDb(UserTable.name eq name).firstOrNull() ?: throw NoSuchUserException()

    private fun extractUser(row: ResultRow) = User(
        row[UserTable.id].value,
        row[UserTable.name],
        row[UserTable.password],
    )

    private suspend fun getUserCollectionFromDb(condition: Op<Boolean>) = dbQuery {
        UserTable
            .select { condition }
            .map(::extractUser)
    }

    private suspend fun <T> dbQuery(block: () -> T): T = withContext(Dispatchers.IO) {
        transaction(database) { block() }
    }
}