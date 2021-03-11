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

    fun getConnection(): Connection =
        if (connection == null) throw ConnectionNotOpenException() else connection as Connection

    fun closeConnection() {
        connection?.close()
    }
}