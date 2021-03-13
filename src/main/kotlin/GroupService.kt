object GroupService {
    /*
        a) Метод, собирающий данные из первого класса-источника, и преобразовывающий
           их в список элементов класса из п4 с использованием данных,
           полученных из второго класса источника

        b) Метод, возвращающий отсортированный по какому-либо полю список из пункта 5а

        c) Метод, группирующий элементы списка из 5а по какому-либо заранее выбранному полю

        d) Метод, возвращающий количество элементов списка из 5а соответствующих переданному
           условию, должен принимать на вход предикат.
    */

    private val groupsWithStudents = GroupDAO.getGroups()
        .map { group -> createGroupWithStudents(group) }

    fun getGroupWithStudents(): List<GroupWithStudents> = groupsWithStudents

    fun getSortedGroupsByNumber(): List<GroupWithStudents> = groupsWithStudents.sortedBy(GroupWithStudents::id)

    fun groupingGroupsBySpecialty(): Map<String, List<GroupWithStudents>> =
        groupsWithStudents.groupBy(GroupWithStudents::specialty)

    fun countBy(predicate: (GroupWithStudents) -> Boolean): Int = groupsWithStudents.count(predicate)

    private fun createGroupWithStudents(group: Group): GroupWithStudents {
        val students = StudentsDAO.getStudentsByGroupId(group.id)
        return GroupWithStudents(group, students)
    }
}
