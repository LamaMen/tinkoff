object GroupDAO {
    private val groups: List<Group> = listOf(
            Group(9001, "Прикладная математика", "ИПММ"),
            Group(9003, "Прикладная математика", "ИПММ"),
            Group(9002, "Прикладаная информатика", "ИКНТ"),
    )

    fun getGroups(): List<Group> = groups
    fun getGroupById(groupId: Int): Group = groups.first { it.id == groupId }
}