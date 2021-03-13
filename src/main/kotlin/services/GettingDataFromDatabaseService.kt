package services

import dao.Department
import dao.Employee
import dao.Project

object GettingDataFromDatabaseService {

    fun getEmployeeById(id: Int): Employee? {
        val sql = "SELECT id, name, department FROM employee WHERE id = ?"
        val resultSet = DatabaseConnectionService.getDataById(sql, id)

        var employee: Employee? = null

        while(resultSet.next()){
            val employeeId = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val department = resultSet.getInt("department")
            employee = Employee(employeeId, name, department)
        }

        resultSet.close()

        return employee
    }

    fun getDepartmentById(id: Int): Department? {
        val sql = "SELECT id, title, telephoneNumber FROM department WHERE id = ?"
        val resultSet = DatabaseConnectionService.getDataById(sql, id)

        var department: Department? = null

        while(resultSet.next()){
            val departmentId = resultSet.getInt("id")
            val title = resultSet.getString("title")
            val telephoneNumber = resultSet.getInt("telephoneNumber")
            department = Department(departmentId, title, telephoneNumber)
        }

        resultSet.close()

        return department
    }

    fun getProjectById(id: Int): Project? {
        val sql = "SELECT id, title, description FROM project WHERE id = ?"
        val resultSet = DatabaseConnectionService.getDataById(sql, id)

        var project: Project? = null

        while(resultSet.next()){
            val projectId = resultSet.getInt("id")
            val title = resultSet.getString("title")
            val description = resultSet.getString("description")
            project = Project(projectId, title, description)
        }

        resultSet.close()

        return project
    }

}