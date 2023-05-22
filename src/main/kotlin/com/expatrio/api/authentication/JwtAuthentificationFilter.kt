package com.expatrio.api.authentication

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userDetailsService: UserDetailsService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        @NotNull request: HttpServletRequest,
        @NotNull response: HttpServletResponse,
        @NotNull filterChain: FilterChain
    ) {
        if(request.servletPath.contains("/api/v1/auth")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader: String? = request.getHeader("Authorization")
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        val jwt = authHeader.substring(7)
        if(jwtService.isTokenValid(jwt) && SecurityContextHolder.getContext().authentication == null) {
            val userEmail = jwtService.extractUsername(jwt)
            val userDetails = userDetailsService.loadUserByUsername(userEmail)
            val authToken = UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.authorities
            );
            authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authToken
        }
        filterChain.doFilter(request, response)
    }
}