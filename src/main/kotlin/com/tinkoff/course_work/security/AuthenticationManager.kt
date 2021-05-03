package com.tinkoff.course_work.security

import com.tinkoff.course_work.exceptions.NoSuchUserException
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthenticationManager(private val jwt: JwtUtil) : ReactiveAuthenticationManager {

    override fun authenticate(authentication: Authentication?): Mono<Authentication> {
        authentication ?: throw NoSuchUserException()
        val token = authentication.credentials.toString()
        return try {
            val username = jwt.extractUsername(token)

            if (jwt.validateToken(token)) {
                Mono.just(extractAuthenticationToken(username, token))
            } else {
                Mono.empty()
            }
        } catch (e: Exception) {
            Mono.empty()
        }
    }

    private fun extractAuthenticationToken(username: String, token: String): Authentication {
        val claims = jwt.getClaimsFromToken(token)
        val role: List<String> = claims["role", List::class.java] as List<String>
        val authorities = role.map { SimpleGrantedAuthority(it) }
        return UsernamePasswordAuthenticationToken(
            username,
            null,
            authorities
        )
    }
}