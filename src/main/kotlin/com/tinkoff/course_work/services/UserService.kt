package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import com.tinkoff.course_work.exceptions.AuthorizationException
import com.tinkoff.course_work.models.domain.User
import com.tinkoff.course_work.security.JwtUtil
import kotlinx.coroutines.reactor.mono
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
    private val dao: UserDAO,
    private val jwt: JwtUtil,
    private val encoder: PasswordEncoder,
) : ReactiveUserDetailsService {
    private val logger = LoggerFactory.getLogger(UserService::class.java)

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val user = mono {
            username ?: throw AuthorizationException()
            getUserByUsername(username)
        }

        return user.cast(UserDetails::class.java)
    }

    suspend fun authenticate(user: User): String {
        val userFromDB = getUserByUsername(user.username)
        if (userFromDB == null || !encoder.matches(user.password, userFromDB.password)) {
            throw AuthorizationException()
        }

        logger.info("User ${user.username} signed in")
        return jwt.createToken(userFromDB.id.toString())
    }

    suspend fun register(user: User): String {
        val userFromDB = getUserByUsername(user.username)
        if (userFromDB != null) throw AuthorizationException()

        val createdUser = dao.addUser(user.username, encoder.encode(user.password))
        logger.info("User ${user.username} registered")
        return jwt.createToken(createdUser)
    }

    private suspend fun getUserByUsername(name: String) = dao.findByUsername(name)
}
