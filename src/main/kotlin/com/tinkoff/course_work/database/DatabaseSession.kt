package com.tinkoff.course_work.database

import com.tinkoff.course_work.exceptions.FactoryNotOpenException
import com.tinkoff.course_work.models.MoneyTransaction
import com.tinkoff.course_work.models.User
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.io.Closeable

object DatabaseSession : Closeable {
    var factory: SessionFactory? = null

    fun openFactory() {
        if (factory == null) {
            factory = Configuration()
                .configure()
                .addAnnotatedClass(User::class.java)
                .addAnnotatedClass(MoneyTransaction::class.java)
                .buildSessionFactory()
        }
    }

    fun getSession(): Session = factory?.currentSession ?: throw FactoryNotOpenException()

    override fun close() {
        factory?.close()
    }
}