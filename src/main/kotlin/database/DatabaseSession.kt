package database

import models.Coast
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration
import java.io.Closeable

object DatabaseSession : Closeable {
    private var factory: SessionFactory? = null

    fun getFactory(): SessionFactory {
        if (factory == null) {
            factory = Configuration()
                .configure()
                .addAnnotatedClass(Coast::class.java)
                .buildSessionFactory()
        }

        return factory!!
    }

    override fun close() {
        factory?.close()
    }
}