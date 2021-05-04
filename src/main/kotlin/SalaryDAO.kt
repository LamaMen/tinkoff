import kotlinx.coroutines.delay

object SalaryDAO {
    private val salaries = listOf(
        EmployeeSalary(1, 10, 1300),
        EmployeeSalary(2, 100, 0),
        EmployeeSalary(3, 5, 150)
    )

    suspend fun getAllSalaries(): List<EmployeeSalary> {
        delay(100)
        return salaries
    }

    suspend fun getSalaryByEmployeeId(id: Int): EmployeeSalary {
        delay(50)
        return salaries.find { it.id == id } ?: throw NoSuchElementException()
    }
}