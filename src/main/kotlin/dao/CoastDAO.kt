package dao

import models.Coast

class CoastDAO {
    private val coasts: MutableList<Coast> = ArrayList()

    fun getAllCoasts(): List<Coast> = coasts

    fun getCoastByTitle(title: String): Coast? = coasts.find { it.title == title }

    fun addCoast(coast: Coast) {
        coasts.add(coast)
    }
}
