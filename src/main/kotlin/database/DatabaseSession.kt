package database

import exceptions.FactoryNotOpenException
import models.Coast
import models.Income
import models.User
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
                .addAnnotatedClass(Coast::class.java)
                .addAnnotatedClass(Income::class.java)
                .buildSessionFactory()
        }
    }

    fun getSession(): Session = factory?.currentSession ?: throw FactoryNotOpenException()

    override fun close() {
        factory?.close()
    }
}