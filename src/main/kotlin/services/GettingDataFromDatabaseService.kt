package services

import dao.*
import java.sql.ResultSet
import java.sql.SQLException

object GettingDataFromDatabaseService {
    private const val employeeByIdSql = "SELECT id, name, department FROM employee WHERE id = ?;"
    private const val departmentByIdSql = "SELECT id, title, phone FROM department WHERE id = ?;"
    private const val projectByIdSql = "SELECT id, title, description FROM project WHERE id = ?;"
    private const val projectsAfterSecondSql = "SELECT id, title, description FROM project WHERE id > 2;"
    private const val employeesWithDepartmentSql = """SELECT e.id, e.name, d.title, d.phone
                                                         FROM employee e
                                                         INNER JOIN department d ON e.department = d.id;"""

    private const val employeeWithProjectSql = """SELECT e.id, e.name, e.department, p.title
                                                  FROM connection
                                                  LEFT JOIN employee e on e.id = connection.employee
                                                  LEFT OUTER JOIN project p on p.id = connection.project;"""

    private const val groupEmployeeByDepartmentSql = """SELECT *
                                                        FROM (SELECT department FROM employee GROUP BY department) AS ed
                                                        LEFT OUTER JOIN employee e on e.department = ed.department;"""

    private const val sortedPhoneSql = """SELECT id, title, phone 
                                            FROM department
                                            ORDER BY phone DESC;"""


    fun getEmployeeById(id: Int): Employee? = getEntityById(id, employeeByIdSql, ::getEmployeeFromResultSet)

    fun getDepartmentById(id: Int): Department? = getEntityById(id, departmentByIdSql, ::getDepartmentFromResultSet)

    fun getProjectById(id: Int): Project? = getEntityById(id, projectByIdSql, ::getProjectFromResultSet)

    fun getProjectsAfterSecond(): List<Project> = getListEntities(projectsAfterSecondSql, ::getProjectFromResultSet)

    fun getEmployeesWithDepartment(): List<EmployeeWithDepartment> =
        getListEntities(employeesWithDepartmentSql, ::getEmployeeWithDepartmentFromResultSet)

    fun getEmployeesWithProjects(): List<EmployeeWithProject> =
        getListEntities(employeeWithProjectSql, ::getEmployeeWithProjectFromResultSet)

    fun groupEmployeesByDepartment(): Map<Int, List<Employee>> {
        return try {
            DatabaseConnectionService.getConnection().createStatement().use { statement ->
                statement.executeQuery(groupEmployeeByDepartmentSql).use { resultSet ->
                    val employeesByDepartment = mutableMapOf<Int, MutableList<Employee>>()

                    while (resultSet.next()) {
                        val employee = getEmployeeFromResultSet(resultSet)
                        val department = employee.departmentId

                        val employees = employeesByDepartment[department] ?: mutableListOf()
                        employees.add(employee)
                        employeesByDepartment[department] = employees
                    }

                    employeesByDepartment
                }
            }
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            emptyMap()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            emptyMap()
        }
    }

    fun sortDepartmentByPhone(): List<Department> = getListEntities(sortedPhoneSql, ::getDepartmentFromResultSet)

    private fun <T> getEntityById(id: Int, sql: String, block: (ResultSet) -> T): T? {
        return try {
            val connection = DatabaseConnectionService.getConnection()
            val statement = connection.prepareStatement(sql)
            statement.setInt(1, id)
            statement.executeQuery().use { resultSet ->
                resultSet.next()
                block(resultSet)
            }
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            null
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            null
        }
    }

    private fun <T> getListEntities(sql: String, block: (ResultSet) -> T): List<T> {
        return try {
            DatabaseConnectionService.getConnection().createStatement().use { statement ->
                statement.executeQuery(sql).use { resultSet ->
                    val entities = mutableListOf<T>()
                    while (resultSet.next()) {
                        entities.add(block(resultSet))
                    }
                    entities
                }
            }
        } catch (e: ConnectionNotOpenException) {
            println("Соединение с базой не установлено")
            emptyList()
        } catch (e: SQLException) {
            println("Ошибка в запросе")
            emptyList()
        }
    }

    private fun getEmployeeFromResultSet(resultSet: ResultSet): Employee {
        val employeeId = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val department = resultSet.getInt("department")

        return Employee(employeeId, name, department)
    }

    private fun getDepartmentFromResultSet(resultSet: ResultSet): Department {
        val departmentId = resultSet.getInt("id")
        val title = resultSet.getString("title")
        val telephoneNumber = resultSet.getInt("phone")

        return Department(departmentId, title, telephoneNumber)
    }

    private fun getProjectFromResultSet(resultSet: ResultSet): Project {
        val projectId = resultSet.getInt("id")
        val title = resultSet.getString("title")
        val description = resultSet.getString("description")

        return Project(projectId, title, description)
    }

    private fun getEmployeeWithDepartmentFromResultSet(resultSet: ResultSet): EmployeeWithDepartment {
        val employeeId = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val departmentTitle = resultSet.getString("title")
        val departmentPhone = resultSet.getInt("phone")

        return EmployeeWithDepartment(employeeId, name, departmentTitle, departmentPhone)
    }

    private fun getEmployeeWithProjectFromResultSet(resultSet: ResultSet): EmployeeWithProject {
        val employeeId = resultSet.getInt("id")
        val name = resultSet.getString("name")
        val department = resultSet.getInt("department")
        val project = resultSet.getString("title")
        return EmployeeWithProject(employeeId, name, department, project)
    }
}
