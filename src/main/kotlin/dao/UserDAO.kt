package dao

import database.DatabaseSession
import models.User

class UserDAO {
    fun getUserByLogin(login: String): User {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        val query = session.createQuery("FROM User as u WHERE u.login=:user_login", User::class.java)
        query.setParameter("user_login", login)
        val user = query.uniqueResult() as User

        session.transaction.commit()
        return user
    }
}