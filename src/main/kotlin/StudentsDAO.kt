object StudentsDAO {
    private val students: List<Student> = listOf(
            Student(1, "Дмитрий", "Иванов", 9001),
            Student(2, "Александр", "Сидоров", 9001),
            Student(3, "Данила", "Медведев", 9002),
            Student(4, "Глеб", "Шишкин", 9003),
            Student(5, "Евгений", "Бутылкин", 9001),
            Student(6, "Денис", "Смирнов", 9003),
            Student(7, "Олег", "Кузнецов", 9002),
    )

    fun getStudents(): List<Student> = students
    fun getStudentsByGroupId(neededGroup: Int): List<Student> = students.filter { it.groupId == neededGroup }
}