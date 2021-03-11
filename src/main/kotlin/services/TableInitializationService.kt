package services

object TableInitializationService {

    fun createTables() {
        val sql = listOf(
            "CREATE TABLE IF NOT EXISTS department (" +
                    "number INT, " +
                    "title VARCHAR(255), " +
                    "telephoneNumber INT, " +
                    "PRIMARY KEY(number)" +
                ");",

            "CREATE TABLE IF NOT EXISTS employee (" +
                    "personnel_number INT, " +
                    "name VARCHAR(255), " +
                    "department INT, " +
                    "PRIMARY KEY(personnel_number), " +
                    "CONSTRAINT fk_department " +
                        "FOREIGN KEY(department) " +
                        "REFERENCES department(number) " +
                        "ON DELETE SET NULL " +
                ");",

            "CREATE TABLE IF NOT EXISTS project (" +
                    "number INT, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "PRIMARY KEY(number));",

            "CREATE TABLE IF NOT EXISTS connection (" +
                    "project INT, " +
                    "employee INT, " +
                    "PRIMARY KEY(project, employee), " +
                    "CONSTRAINT fk_project " +
                        "FOREIGN KEY(project) " +
                            "REFERENCES project(number) " +
                            "ON DELETE CASCADE," +
                    "CONSTRAINT fk_employee " +
                        "FOREIGN KEY(employee) " +
                            "REFERENCES employee(personnel_number) " +
                            "ON DELETE CASCADE" +
                ");"
        )

        executeMultipleUpdate(sql)
    }

    fun deleteTables() {
        val sql = listOf(
            "DROP TABLE IF EXISTS connection;",
            "DROP TABLE IF EXISTS project;",
            "DROP TABLE IF EXISTS employee;",
            "DROP TABLE IF EXISTS department;",
        )

        executeMultipleUpdate(sql)
    }

    private fun executeMultipleUpdate(sql: List<String>) {
        // TODO Check exception
        DatabaseConnectionService.getConnection().createStatement().use { statement ->
            sql.forEach { statement.executeUpdate(it) }
        }
    }
}