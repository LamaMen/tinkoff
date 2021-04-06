interface DataBase {
    fun getNames(): List<String>
    fun getNameById(id: Int): String?
}