package dao

data class Employee(val personnelNumber: Int, val name: String, val department: String)

data class Departament(val number: Int, val title: String, val telephoneNumber: Int)

data class Project(val number: Int, val title: String, val description: String)

data class ProjectStaff(val project: Int, val employee: Int)