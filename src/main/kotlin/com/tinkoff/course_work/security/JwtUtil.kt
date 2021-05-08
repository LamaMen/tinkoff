package com.tinkoff.course_work.security

import com.tinkoff.course_work.models.domain.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtUtil {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.expiration}")
    private lateinit var expirationTime: String

    fun createToken(userId: String?): String {
        val claims = HashMap<String, Any>()
        claims["role"] = listOf(UserRole.ROLE_USER)

        val expirationSeconds = expirationTime.toLong()
        val currentDate = Date()
        val expirationDate = Date(currentDate.time + expirationSeconds * 1000)

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(userId)
            .setIssuedAt(currentDate)
            .setExpiration(expirationDate)
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }

    fun extractUsername(token: String): String {
        return getClaimsFromToken(token).subject
    }

    fun validateToken(token: String) =
        getClaimsFromToken(token)
            .expiration.after(Date())


    fun getClaimsFromToken(token: String): Claims {
        val key = Base64.getEncoder().encodeToString(secret.toByteArray())

        return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}

