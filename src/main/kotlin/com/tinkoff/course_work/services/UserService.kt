package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import org.springframework.stereotype.Service

@Service
class UserService(private val dao: UserDAO) {
    fun getUserByLogin(login: String) = dao.getUserByLogin(login)
}