package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import org.springframework.stereotype.Service

@Service
class UserService(private val dao: UserDAO) {
    suspend fun getUserByLogin(name: String) = dao.findByUsername(name)

//    override fun findByUsername(username: String?): Mono<UserDetails> {
//        val user = mono {
//            val name = username ?: throw NoSuchUserException()
//            dao.findByUsername(username)
//        }
//        return user.cast(UserDetails::class.java)
//    }
}