package com.example.rest.interfaceAdaptersLayer.security

import com.example.rest.businessLayer.boundaries.UserSecurity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.crypto.SecretKey


class UserSecurityImpl(key: String) : UserSecurity {

    private val signInKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key))

    private val tokenDuration = Duration.ofDays(1)

    override fun getHash(password: String): String {
        val encoder = BCryptPasswordEncoder()
        val hashedPassword = encoder.encode(password)
        return hashedPassword
    }

    override fun checkPassword(password: String, hash: String): Boolean {
        val encoder = BCryptPasswordEncoder()
        return encoder.matches(password, hash)
    }

    override fun generateToken(username: String): String {
        val now = Instant.now()
        val expiration = now.plus(tokenDuration)
        val token: String = Jwts
            .builder()
            .subject(username)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiration))
            .signWith(signInKey, Jwts.SIG.HS256)
            .compact()
        return token
    }

    override fun validateToken(token: String): Result<Boolean> {
        return try {
            Result.success(!isTokenExpired(token))
        } catch (e: Exception) {
            Result.failure(IllegalArgumentException("Invalid token"))
        }
    }

    override fun tokenUser(token: String): String {
        return extractUsername(token)
    }

    private fun extractUsername(token: String) = extractAllClaims(token).subject

    private fun extractExpiration(token: String) = extractAllClaims(token).expiration

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date.from(Instant.now()))
    }

    private fun extractAllClaims(token: String): Claims {
        val payload = Jwts
            .parser()
            .verifyWith(signInKey)
            .build()
            .parseSignedClaims(token)
            .payload
        return payload
    }
}