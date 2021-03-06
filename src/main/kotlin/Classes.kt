data class Student(val studentNumber: Int, val name: String, val surname: String, val groupNumber: Int) {
    override fun toString(): String = "$name $surname, группа $groupNumber"
}

data class Group(val number: Int, val specialty: String, val faculty: String) {
    override fun toString(): String = "Группа $number, $faculty, $specialty"
}

data class GroupWithStudents(val number: Int, val specialty: String, val faculty: String, val students: List<Student>) {

    constructor(group: Group) : this(
            group.number,
            group.specialty,
            group.faculty,
            StudentsDAO.getStudentsByGroupNumber(group.number)
    )

    override fun toString(): String = "Группа $number, $faculty, $specialty, стуженты: $students"

}