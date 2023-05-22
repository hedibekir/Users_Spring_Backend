package com.expatrio.api.authentication

import com.expatrio.api.model.response.ErrorResponse
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class JwtAccessDeniedHandler(

): AccessDeniedHandler{
    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException
    ) {
        val errorResponse = ErrorResponse(HttpStatus.FORBIDDEN.toString(), accessDeniedException.message, arrayListOf())
        val out = response.outputStream
        val mapper = jacksonObjectMapper()
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        mapper.writeValue(out, errorResponse)
        out.flush()

    }
}