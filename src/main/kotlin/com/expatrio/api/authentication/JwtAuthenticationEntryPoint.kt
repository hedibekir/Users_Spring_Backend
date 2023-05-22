package com.expatrio.api.authentication

import com.expatrio.api.model.response.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint(
): AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val errorResponse = ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), authException.message, arrayListOf())
        val out = response.outputStream
        val mapper = jacksonObjectMapper()
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        mapper.writeValue(out, errorResponse)
        out.flush()
    }
}