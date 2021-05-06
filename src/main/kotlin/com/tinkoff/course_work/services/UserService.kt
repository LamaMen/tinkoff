package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import com.tinkoff.course_work.exceptions.NoSuchUserException
import com.tinkoff.course_work.models.User
import com.tinkoff.course_work.security.JwtUtil
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.security.Principal

@Service
class UserService(
    private val dao: UserDAO,
    private val jwt: JwtUtil
) : ReactiveUserDetailsService {

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val user = mono {
            username ?: throw NoSuchUserException()
            getUserByUsername(username)
        }

        return user.cast(UserDetails::class.java)
    }

    suspend fun authenticate(user: User): String? {
        val userFromDB = getUserByUsername(user.username)

        return if (userFromDB != null && userFromDB.password == user.password) {
            jwt.createToken(userFromDB.id)
        } else {
            null
        }
    }

    suspend fun register(user: User): String? {
        val userFromDB = getUserByUsername(user.username)
        if (userFromDB != null) return null

        val createdUser = dao.addUser(user)
        return jwt.createToken(createdUser)
    }

    fun decodeId(principal: Principal) = try {
        principal.name.toInt()
    } catch (e: NumberFormatException) {
        throw NoSuchUserException()
    }

    private suspend fun getUserByUsername(name: String) = dao.findByUsername(name)
}
