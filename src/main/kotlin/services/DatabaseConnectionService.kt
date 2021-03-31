package services

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object DatabaseConnectionService {
    private lateinit var connection: Connection

    fun connectWithDatabase(dbUrl: String, user: String, password: String) {
        try {
            connection = DriverManager.getConnection(dbUrl, user, password)
        } catch (e: SQLException) {
            println("Ошибка подключения к базе данных!")
        }
    }

    fun getConnection() = if (::connection.isInitialized) connection else throw ConnectionNotOpenException()

    fun closeConnection() {
        connection.close()
    }
}