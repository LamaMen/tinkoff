data class Student(val studentNumber: Int, val name: String, val surname: String, val groupNumber: Int)
data class Group(val number: Int, val specialty: String, val faculty: String)

data class GroupWithStudents(val number: Int, val specialty: String, val faculty: String, val students: List<Student>) {

    constructor(group: Group) : this(
            group.number,
            group.specialty,
            group.faculty,
            StudentsDAO.getStudentsByGroupNumber(group.number)
    )

}