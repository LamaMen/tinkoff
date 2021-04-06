class Museum(private val pictures: List<String>) {
    fun contains(picture: String) = pictures.contains(picture)
    fun getById(id: Int): String? = pictures.getOrNull(id)
}
