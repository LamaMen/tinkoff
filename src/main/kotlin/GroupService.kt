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
            .map { group ->
                GroupWithStudents(
                        group.number,
                        group.specialty,
                        group.faculty,
                        StudentsDAO.getStudentsByGroupNumber(group.number)
                )
            }

    fun getGroupWithStudents() = groupsWithStudents
    fun getSortedGroupsByNumber(): List<GroupWithStudents> = groupsWithStudents.sortedBy { it.number }
    fun filterGroupByFaculty(faculty: String): List<GroupWithStudents> = groupsWithStudents.filter { it.faculty == faculty }
    fun countBy(predicate: (GroupWithStudents) -> Boolean) = groupsWithStudents.count(predicate)


}