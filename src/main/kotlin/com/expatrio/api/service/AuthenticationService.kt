package com.expatrio.api.service

import com.expatrio.api.authentication.JwtService
import com.expatrio.api.model.request.AuthenticationRequest
import com.expatrio.api.model.response.AuthenticationResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val authenticationManager: AuthenticationManager,
    private val userDetailsService: UserDetailsService,
    private val jwtService: JwtService
) {
    fun authenticate(authenticationRequest: AuthenticationRequest): AuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequest.email,
                authenticationRequest.password
            )
        )

        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.email)
        val jwtToken = jwtService.generateToken(userDetails)
        val jwtRefreshToken = jwtService.generateRefreshToken(userDetails)
        return AuthenticationResponse(
            jwtToken,
            jwtRefreshToken
        )
    }

    fun refreshToken(
        request: HttpServletRequest
    ): AuthenticationResponse? {
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return null
        }
        val refreshToken = authHeader.substring(7)
        if (jwtService.isTokenValid(refreshToken)) {
            val userEmail = jwtService.extractUsername(refreshToken)
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            val accessToken = jwtService.generateToken(userDetails)
            return AuthenticationResponse(
                accessToken,
                refreshToken
            )
        }
        return null
    }
}