fun List<GroupWithStudents>.print(): String = this.joinToString(separator = ", ") { it.id.toString() }

fun main() {
    println("Все студенты:")
    for (student in StudentsDAO.getStudents()) {
        println("  $student")
    }
    println()

    println("Все группы:")
    for (group in GroupDAO.getGroups()) {
        println("  $group")
    }
    println()

    println("Студенты группы 9001:")
    for (student in StudentsDAO.getStudentsByGroupId(9001)) {
        println("  $student")
    }
    println()

    println("Группы с студентами:")
    for (group in GroupService.getGroupWithStudents()) {
        println("  $group")
    }
    println()

    println("Отсортированный по номеру группы список групп с студентами:")
    for (group in GroupService.getSortedGroupsByNumber()) {
        println("  $group")
    }
    println()

    println("Группы сгруппированные по специальностям:")
    for (group in GroupService.groupingGroupsBySpecialty()) {
        println("  ${group.key}: ${group.value.print()}")
    }
    println()

    println("Количество групп, в которых менее 3 студентов: ${GroupService.countBy { it.students.size < 3 }}")
}