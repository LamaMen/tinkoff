package services

import dao.*
import java.sql.SQLException

object GettingDataFromDatabaseService {


    fun getEmployeeById(id: Int): Employee? {
        try {
            val sql = "SELECT id, name, department FROM employee WHERE id = ?;"
            val resultSet = DatabaseConnectionService.getDataById(sql, id)

            var employee: Employee? = null

            while (resultSet.next()) {
                val employeeId = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val department = resultSet.getInt("department")
                employee = Employee(employeeId, name, department)
            }

            resultSet.close()

            return employee
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return null
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return null
        }
    }

    fun getDepartmentById(id: Int): Department? {
        try {
            val sql = "SELECT id, title, phone FROM department WHERE id = ?;"
            val resultSet = DatabaseConnectionService.getDataById(sql, id)

            var department: Department? = null

            while (resultSet.next()) {
                val departmentId = resultSet.getInt("id")
                val title = resultSet.getString("title")
                val telephoneNumber = resultSet.getInt("phone")
                department = Department(departmentId, title, telephoneNumber)
            }

            resultSet.close()

            return department
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return null
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return null
        }
    }

    fun getProjectById(id: Int): Project? {
        try {
            val sql = "SELECT id, title, description FROM project WHERE id = ?;"
            val resultSet = DatabaseConnectionService.getDataById(sql, id)

            var project: Project? = null

            while (resultSet.next()) {
                val projectId = resultSet.getInt("id")
                val title = resultSet.getString("title")
                val description = resultSet.getString("description")
                project = Project(projectId, title, description)
            }

            resultSet.close()

            return project
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return null
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return null
        }
    }

    fun getProjectsAfterSecond(): List<Project> {
        try {
            val sql = "SELECT id, title, description FROM project WHERE id > 2;"
            val resultSet = DatabaseConnectionService.executeData(sql)

            val projects = mutableListOf<Project>()

            while (resultSet.next()) {
                val projectId = resultSet.getInt("id")
                val title = resultSet.getString("title")
                val description = resultSet.getString("description")
                projects.add(Project(projectId, title, description))
            }

            resultSet.close()

            return projects
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return listOf()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return listOf()
        }
    }

    fun getEmployeesWithDepartment(): List<EmployeeWithDepartment> {
        try {
            val sql = "SELECT e.id, e.name, d.title, d.phone " +
                    "FROM employee e " +
                    "INNER JOIN department d ON e.department = d.id;"

            val resultSet = DatabaseConnectionService.executeData(sql)

            val employees = mutableListOf<EmployeeWithDepartment>()

            while (resultSet.next()) {
                val employeeId = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val departmentTitle = resultSet.getString("title")
                val departmentPhone = resultSet.getInt("phone")
                employees.add(EmployeeWithDepartment(employeeId, name, departmentTitle, departmentPhone))
            }

            resultSet.close()

            return employees
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return listOf()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return listOf()
        }
    }

    fun getEmployeesWithProjects(): List<EmployeeWithProject> {
        try {
            val sql = "SELECT e.id, e.name, e.department, p.title " +
                    "FROM connection " +
                    "LEFT JOIN employee e on e.id = connection.employee " +
                    "LEFT OUTER JOIN project p on p.id = connection.project;"

            val resultSet = DatabaseConnectionService.executeData(sql)

            val employees = mutableListOf<EmployeeWithProject>()

            while (resultSet.next()) {
                val employeeId = resultSet.getInt("id")
                val name = resultSet.getString("name")
                val department = resultSet.getInt("department")
                val project = resultSet.getString("title")
                employees.add(EmployeeWithProject(employeeId, name, department, project))
            }

            resultSet.close()

            return employees
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return listOf()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return listOf()
        }
    }

    fun groupEmployeesByDepartment(): Map<Int, List<Employee>> {
        try {
            val sql = "SELECT * " +
                    "FROM (SELECT department FROM employee GROUP BY department) AS ed " +
                    "LEFT OUTER JOIN employee e on e.department = ed.department;"

            val resultSet = DatabaseConnectionService.executeData(sql)

            val employeesByDepartment = mutableMapOf<Int, MutableList<Employee>>()

            while (resultSet.next()) {
                val department = resultSet.getInt("department")
                val id = resultSet.getInt("id")
                val name = resultSet.getString("name")

                if (!employeesByDepartment.containsKey(department)) {
                    employeesByDepartment[department] = mutableListOf()
                }

                val employees = employeesByDepartment[department]
                employees?.add(Employee(id, name, department))
            }

            resultSet.close()

            return employeesByDepartment
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return mapOf()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return mapOf()
        }
    }

    fun sortDepartmentByPhone(): List<Department> {
        try {
            val sql = "SELECT id, title, phone " +
                    "FROM department " +
                    "ORDER BY phone DESC;"

            val resultSet = DatabaseConnectionService.executeData(sql)

            val departments = mutableListOf<Department>()

            while (resultSet.next()) {
                val id = resultSet.getInt("id")
                val title = resultSet.getString("title")
                val phone = resultSet.getInt("phone")
                departments.add(Department(id, title, phone))
            }

            resultSet.close()

            return departments
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            return listOf()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            return listOf()
        }
    }

}