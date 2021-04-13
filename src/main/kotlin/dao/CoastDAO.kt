package dao

import database.DatabaseSession
import models.Coast
import models.User

class CoastDAO {
    fun getCoastById(id: Int): Coast {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        val coast = session.get(Coast::class.java, id)

        session.transaction.commit()
        return coast
    }

    fun getAllCoastsByUser(user: User): List<Coast> {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        val query =
            session.createQuery("SELECT c FROM Coast c LEFT JOIN FETCH c.user where c.user=:user", Coast::class.java)
        query.setParameter("user", user)
        val coasts = query.list()

        session.transaction.commit()
        return coasts as List<Coast>
    }

    fun addCoast(coast: Coast, user: User) {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        coast.user = user
        session.save(coast)

        session.transaction.commit()
    }

    fun deleteCoast(coast: Coast) {
        val session = DatabaseSession.getSession()
        session.beginTransaction()
        session.delete(coast)
        session.transaction.commit()
    }

}
