data class Student(val studentId: Int, val name: String, val surname: String, val groupId: Int) {
    override fun toString(): String = "$name $surname, группа $groupId"
}

data class Group(val id: Int, val specialty: String, val faculty: String) {
    override fun toString(): String = "Группа $id, $faculty, $specialty"
}

data class GroupWithStudents(val id: Int, val specialty: String, val faculty: String, val students: List<Student>) {

    constructor(group: Group, students: List<Student>) : this(
        group.id,
        group.specialty,
        group.faculty,
        students,
    )

    override fun toString(): String = "Группа $id, $faculty, $specialty, стуженты: $students"

}