package dao

data class Employee(val id: Int, val name: String, val department: Int)

data class Department(val id: Int, val title: String, val phone: Int)

data class Project(val id: Int, val title: String, val description: String)

data class ProjectStaff(val project: Int, val employee: Int)