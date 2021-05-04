object EmployeeService {
    suspend fun getAllEmployees(): List<EmployeeWithSalary> {
        val employees = EmployeeDAO.getAllEmployees()
        val salaries = SalaryDAO.getAllSalaries()

        return employees.map { employee ->
            val salary = salaries.first { it.id == employee.id }
            groupEmployeeWithSalary(employee, salary)
        }
    }

    suspend fun getEmployeeWithSalaryById(id: Int): EmployeeWithSalary {
        val employee = EmployeeDAO.getEmployeeById(id)
        val salary = SalaryDAO.getSalaryByEmployeeId(id)

        return groupEmployeeWithSalary(employee, salary)
    }

    private fun groupEmployeeWithSalary(employee: Employee, salary: EmployeeSalary) =
        EmployeeWithSalary(employee.id, employee.name, salary.salary, salary.balance)
}