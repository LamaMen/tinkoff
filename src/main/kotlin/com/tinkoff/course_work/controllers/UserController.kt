package com.tinkoff.course_work.controllers

import com.tinkoff.course_work.models.User
import com.tinkoff.course_work.services.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val service: UserService) {
    private val unauthorized = ResponseEntity.status(HttpStatus.UNAUTHORIZED).build<String>()

    @PostMapping("/login")
    suspend fun login(@RequestBody user: User): ResponseEntity<String> {
        val token = service.authenticate(user)
        return if (token != null) ResponseEntity.ok(token) else unauthorized
    }
}