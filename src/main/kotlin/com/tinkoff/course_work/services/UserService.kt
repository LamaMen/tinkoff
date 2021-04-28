package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import com.tinkoff.course_work.exceptions.NoSuchUserException
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(private val dao: UserDAO) : ReactiveUserDetailsService {
    suspend fun getUserByLogin(name: String) = dao.findByUsername(name)

    override fun findByUsername(username: String?): Mono<UserDetails> {
        val user = mono {
            username ?: throw NoSuchUserException()
            dao.findByUsername(username)
        }

        return user.cast(UserDetails::class.java)
    }
}