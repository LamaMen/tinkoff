package dao

data class EmployeeWithDepartment(val employeeId: Int, val name: String, val departmentTitle: String, val departmentTelephoneNumber: Int)

data class EmployeeWithProject(val employeeId: Int, val name: String, val department: Int, val projectTitle: String)