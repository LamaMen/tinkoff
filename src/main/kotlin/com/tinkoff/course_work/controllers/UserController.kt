package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.Token
import com.tinkoff.course_work.models.User
import com.tinkoff.course_work.services.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val service: UserService) {
    @PostMapping("/login")
    suspend fun login(@RequestBody user: User): Token {
        val token = service.authenticate(user)
        return Token(token)
    }

    @PostMapping("/register")
    suspend fun register(@RequestBody user: User): Token {
        val token = service.register(user)
        return Token(token)
    }
}