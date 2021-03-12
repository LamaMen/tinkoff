package services

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnectionService {
    private var connection: Connection? = null

    fun connectWithDatabase(dbUrl: String, user: String, password: String) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, password)
        } catch (e: SQLException) {
            println("Ошибка подключения к базе данных!")
        }
    }

    fun executeMultipleUpdate(sql: List<String>) {
        if (connection == null) return

        try {
            connection!!.createStatement().use { statement ->
                sql.forEach { statement.executeUpdate(it) }
            }
        } catch (e: SQLException) {
            println("Ошибка в запросе." + e.message)
        }
    }

    fun closeConnection() {
        connection?.close()
    }
}