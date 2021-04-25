package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.UserTable
import com.tinkoff.course_work.exceptions.NoSuchUserException
import com.tinkoff.course_work.models.User
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

@Repository
class UserDAO(private val database: Database) {
    fun getUserByLogin(login: String): User =
        getUserCollectionFromDb(UserTable.login eq login).firstOrNull() ?: throw NoSuchUserException()

    private fun extractUser(row: ResultRow) = User(
        row[UserTable.id].value,
        row[UserTable.name],
        row[UserTable.login],
        row[UserTable.password],
    )

    private fun getUserCollectionFromDb(condition: Op<Boolean>) = transaction(database) {
        UserTable
            .select { condition }
            .map(::extractUser)
    }
}