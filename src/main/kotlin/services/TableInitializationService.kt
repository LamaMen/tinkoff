package services

import services.DatabaseConnectionService.executeMultipleUpdate

object TableInitializationService {

    fun createTables() {
        val sql = listOf(
            "CREATE TABLE IF NOT EXISTS department (" +
                    "id INT, " +
                    "title VARCHAR(255), " +
                    "telephoneNumber INT, " +
                    "PRIMARY KEY(id)" +
                    ");",

            "CREATE TABLE IF NOT EXISTS employee (" +
                    "id INT, " +
                    "name VARCHAR(255), " +
                    "department INT, " +
                    "PRIMARY KEY(id), " +
                    "CONSTRAINT fk_department " +
                        "FOREIGN KEY(department) " +
                            "REFERENCES department(id) " +
                            "ON DELETE SET NULL " +
                    ");",

            "CREATE TABLE IF NOT EXISTS project (" +
                    "id INT, " +
                    "title VARCHAR(255), " +
                    "description VARCHAR(255), " +
                    "PRIMARY KEY(id));",

            "CREATE TABLE IF NOT EXISTS connection (" +
                    "project INT, " +
                    "employee INT, " +
                    "PRIMARY KEY(project, employee), " +
                    "CONSTRAINT fk_project " +
                        "FOREIGN KEY(project) " +
                            "REFERENCES project(id) " +
                            "ON DELETE CASCADE," +
                    "CONSTRAINT fk_employee " +
                        "FOREIGN KEY(employee) " +
                            "REFERENCES employee(id) " +
                            "ON DELETE CASCADE" +
                    ");"
        )

        executeMultipleUpdate(sql)
    }

    fun insertDataInTables() {
        val sql = listOf(
            "INSERT INTO department VALUES" +
                " (1, 'IT', 1111111) ON CONFLICT DO NOTHING;",

            "INSERT INTO department VALUES" +
                    " (2, 'Продаж', 5553535) ON CONFLICT DO NOTHING;",

            "INSERT INTO department VALUES" +
                    " (3, 'Маркетинг', 5681256) ON CONFLICT DO NOTHING;",

            "INSERT INTO department VALUES" +
                    " (4, 'Кадры', 2225609) ON CONFLICT DO NOTHING;",

            "INSERT INTO department VALUES" +
                    " (5, 'Юридический', 8918989) ON CONFLICT DO NOTHING;",


            "INSERT INTO employee VALUES" +
                    " (1, 'Михаил', 5) ON CONFLICT DO NOTHING;",

            "INSERT INTO employee VALUES" +
                    " (2, 'Данила', 2) ON CONFLICT DO NOTHING;",

            "INSERT INTO employee VALUES" +
                    " (3, 'Иван', 1) ON CONFLICT DO NOTHING;",

            "INSERT INTO employee VALUES" +
                    " (4, 'Максим', 4) ON CONFLICT DO NOTHING;",

            "INSERT INTO employee VALUES" +
                    " (5, 'Кирилл', 3) ON CONFLICT DO NOTHING;",


            "INSERT INTO project VALUES" +
                    " (1, 'РПО-01', 'Первый проект предприятия') ON CONFLICT DO NOTHING;",

            "INSERT INTO project VALUES" +
                    " (3, 'СДП-01', 'Третий проект предприятия') ON CONFLICT DO NOTHING;",

            "INSERT INTO project VALUES" +
                    " (4, 'СДП-02', 'Четвертый проект предприятия') ON CONFLICT DO NOTHING;",

            "INSERT INTO project VALUES" +
                    " (5, 'СДП-03', 'Пятый проект предприятия') ON CONFLICT DO NOTHING;",

            "INSERT INTO project VALUES" +
                    " (2, 'РПО-02', 'Второй проет предприятия') ON CONFLICT DO NOTHING;",


            "INSERT INTO connection VALUES" +
                    " (3, 5) ON CONFLICT DO NOTHING;",

            "INSERT INTO connection VALUES" +
                    " (1, 3) ON CONFLICT DO NOTHING;",

            "INSERT INTO connection VALUES" +
                    " (1, 4) ON CONFLICT DO NOTHING;",

            "INSERT INTO connection VALUES" +
                    " (5, 4) ON CONFLICT DO NOTHING;",

            "INSERT INTO connection VALUES" +
                    " (3, 4) ON CONFLICT DO NOTHING;",
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
}