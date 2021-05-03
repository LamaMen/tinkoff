package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.User
import com.tinkoff.course_work.security.JwtUtil
import com.tinkoff.course_work.services.UserService
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val service: UserService,
    private val jwt: JwtUtil
) {
    private val unauthorized = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<String>()

    @PostMapping("/login")
    suspend fun login(@RequestBody user: User): ResponseEntity<String> {
        val userFromDB = service.findByUsername(user.username).cast(User::class.java)
        val response = userFromDB.map {
            if (it.password == user.password) {
                ResponseEntity.ok(jwt.createToken(user))
            } else {
                unauthorized
            }
        }

        return response.awaitFirstOrNull() ?: unauthorized
    }
}