import kotlinx.coroutines.channels.ReceiveChannel

data class EmployeeWithSalary(val id: Int, val name: String, val salary: Int, var balance: Long) {
    lateinit var salaryChanel: ReceiveChannel<Int>
}

data class Employee(val id: Int, val name: String)

data class EmployeeSalary(val id: Int, val salary: Int, val balance: Long)
