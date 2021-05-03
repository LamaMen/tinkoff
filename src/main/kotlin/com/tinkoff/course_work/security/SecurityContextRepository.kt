package com.tinkoff.course_work.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.web.server.context.ServerSecurityContextRepository
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class SecurityContextRepository(private val authenticationManager: AuthenticationManager) :
    ServerSecurityContextRepository {
    override fun save(exchange: ServerWebExchange?, context: SecurityContext?): Mono<Void> {
        throw IllegalStateException("Save method not supported!")
    }

    override fun load(exchange: ServerWebExchange?): Mono<SecurityContext> {
        exchange ?: throw IllegalArgumentException()
        val header = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)

        return if (header != null && header.startsWith("Bearer ")) {
            val token = header.substring(7)
            val auth = UsernamePasswordAuthenticationToken(token, token)

            authenticationManager
                .authenticate(auth)
                .map { SecurityContextImpl(it) }

        } else {
            Mono.empty()
        }
    }
}