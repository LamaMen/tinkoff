package com.tinkoff.course_work.dao

import com.tinkoff.course_work.database.DatabaseSession
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User

class MoneyTransactionDAO {
    fun getTransactionById(id: Int): MoneyTransaction {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        val coast = session.get(MoneyTransaction::class.java, id)

        session.transaction.commit()
        return coast
    }

    fun getAllCoastsByUser(user: User): List<MoneyTransaction> {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        val query =
            session.createQuery(
                "SELECT t FROM MoneyTransaction t LEFT JOIN FETCH t.user where t.user=:user",
                MoneyTransaction::class.java
            )
        query.setParameter("user", user)
        val coasts = query.list()

        session.transaction.commit()
        return coasts
    }

    fun addCoast(moneyTransaction: MoneyTransaction, user: User) {
        val session = DatabaseSession.getSession()
        session.beginTransaction()

        moneyTransaction.user = user
        session.save(moneyTransaction)

        session.transaction.commit()
    }

    fun deleteCoast(moneyTransaction: MoneyTransaction) {
        val session = DatabaseSession.getSession()
        session.beginTransaction()
        session.delete(moneyTransaction)
        session.transaction.commit()
    }

}
