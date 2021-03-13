package services

import dao.*

object GettingDataFromDatabaseService {

    fun getEmployeeById(id: Int): Employee? {
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
    }

    fun getDepartmentById(id: Int): Department? {
        val sql = "SELECT id, title, telephoneNumber FROM department WHERE id = ?;"
        val resultSet = DatabaseConnectionService.getDataById(sql, id)

        var department: Department? = null

        while (resultSet.next()) {
            val departmentId = resultSet.getInt("id")
            val title = resultSet.getString("title")
            val telephoneNumber = resultSet.getInt("telephoneNumber")
            department = Department(departmentId, title, telephoneNumber)
        }

        resultSet.close()

        return department
    }

    fun getProjectById(id: Int): Project? {
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
    }

    fun getProjectsAfterSecond(): List<Project> {
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
    }

    fun getEmployeesWithDepartment(): List<EmployeeWithDepartment> {
        val sql = "SELECT e.id, e.name, d.title, d.telephoneNumber " +
                "FROM employee e " +
                "INNER JOIN department d ON e.department = d.id;"

        val resultSet = DatabaseConnectionService.executeData(sql)

        val employees = mutableListOf<EmployeeWithDepartment>()

        while (resultSet.next()) {
            val employeeId = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val departmentTitle = resultSet.getString("title")
            val departmentPhone = resultSet.getInt("telephoneNumber")
            employees.add(EmployeeWithDepartment(employeeId, name, departmentTitle, departmentPhone))
        }

        resultSet.close()

        return employees
    }

    fun getEmployeesWithProjects(): List<EmployeeWithProject> {
        val sql = "SELECT e.id, e.name, e.department, p.title " +
                "FROM connection " +
                "LEFT JOIN employee e on e.id = connection.employee " +
                "LEFT OUTER JOIN project p on p.id = connection.project;"

        val resultSet = DatabaseConnectionService.executeData(sql)

        val employees = mutableListOf<EmployeeWithProject>()

        while(resultSet.next()){
            val employeeId = resultSet.getInt("id")
            val name = resultSet.getString("name")
            val department = resultSet.getInt("department")
            val project = resultSet.getString("title")
            employees.add(EmployeeWithProject(employeeId, name, department, project))
        }

        resultSet.close()

        return employees
    }


}