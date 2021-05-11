@file:Suppress("DEPRECATION")

package com.tinkoff.course_work.services

import com.tinkoff.course_work.dao.UserDAO
import com.tinkoff.course_work.exceptions.AuthorizationException
import com.tinkoff.course_work.models.domain.User
import com.tinkoff.course_work.security.JwtUtil
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.security.crypto.password.NoOpPasswordEncoder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserServiceTest {
    private val encoder = NoOpPasswordEncoder.getInstance()
    private lateinit var dao: UserDAO
    private lateinit var jwt: JwtUtil
    private lateinit var service: UserService

    private val testUser = User("1", "admin", encoder.encode("admin"))

    @BeforeAll
    internal fun beforeAll() {
        dao = mockk {
            coEvery { findByUsername(any()) } returns null
            coEvery { findByUsername("admin") } returns testUser
        }

        jwt = mockk {
            every { createToken("test") } returns "test"
            every { createToken(testUser.id) } returns testUser.id!!
        }

        service = UserService(dao, jwt, encoder)
    }

    @Test
    fun `find user by username`() = runBlocking {
        val user = service.findByUsername("admin").block()

        assertNotNull(user)
        assertEquals(testUser.username, user?.username)
        assertEquals("admin", user?.password)
    }

    @Test
    fun `find user by username with wrong username`() = runBlocking {
        val user = service.findByUsername("test").block()
        assertNull(user)
    }

    @Test
    fun `authenticate correct user`() = runBlocking {
        val token = service.authenticate(testUser)
        assertEquals(testUser.id, token)
    }

    @Test
    fun `authenticate non-existent user`() {
        val nonExistentUser = User("2", "test", encoder.encode("test"))
        assertThrows(AuthorizationException::class.java) { runBlocking { service.authenticate(nonExistentUser) } }
    }

    @Test
    fun `authenticate user with wrong password`() {
        val userWithWrongPassword = User("1", "admin", encoder.encode("wrong password"))
        assertThrows(AuthorizationException::class.java) { runBlocking { service.authenticate(userWithWrongPassword) } }
    }

    @Test
    fun `register new user`() = runBlocking {
        val newUser = User("2", "test", encoder.encode("test"))

        coEvery { dao.addUser(newUser.username, newUser.password) } returns newUser.username

        val token = service.register(newUser)
        assertEquals("test", token)
    }

    @Test
    fun `register exist user`() {
        assertThrows(AuthorizationException::class.java) { runBlocking { service.register(testUser) } }
    }
}
