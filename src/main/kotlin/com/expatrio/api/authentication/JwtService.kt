package com.expatrio.api.authentication

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function


@Service
class JwtService(
    @Value("\${jwt.secret}") private val jwtSecretKey: String,
    @Value("\${jwt.expiration}") private val jwtExpiration: Long,
    @Value("\${jwt.refresh-token.expiration}") private val jwtRefreshExpiration: Long
) {

    fun generateToken(
        userDetails: UserDetails
    ): String {
        return buildToken(userDetails, jwtExpiration)
    }

    fun generateRefreshToken(
        userDetails: UserDetails
    ): String {
        return buildToken(userDetails, jwtRefreshExpiration)
    }

    fun isTokenValid(token: String): Boolean {
        val username = extractUsername(token)
        return (username != null)  && !isTokenExpired(token)!!
    }

    fun extractUsername(token: String): String? {
        return extractClaim(token) { obj: Claims? -> obj!!.subject }
    }

    fun extractExpiration(token: String): Date? {
        return extractClaim(token) { obj: Claims? -> obj!!.expiration }
    }

    fun isTokenExpired(token: String): Boolean? {
        return extractExpiration(token)?.before(Date())
    }

    private fun buildToken(
        userDetails: UserDetails,
        expiration: Long
    ) : String {
        return Jwts
            .builder()
            .setSubject(userDetails.username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact()
    }

    private fun getSignInKey(): Key {
        val keyBytes = Decoders.BASE64.decode(jwtSecretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims?, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String): Claims? {
        return Jwts
            .parserBuilder()
            .setSigningKey(getSignInKey())
            .build()
            .parseClaimsJws(token)
            .body
    }
}