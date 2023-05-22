package com.expatrio.api.controller

import com.expatrio.api.service.AuthenticationService
import com.expatrio.api.model.request.AuthenticationRequest
import com.expatrio.api.model.response.AuthenticationResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/authenticate")
    fun authenticate(@Valid @RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<AuthenticationResponse> {
        return ResponseEntity(authenticationService.authenticate(authenticationRequest), HttpStatus.OK)
    }

    @GetMapping("/refresh-token")
    fun refreshToken(request: HttpServletRequest): ResponseEntity<AuthenticationResponse> {
        val response: AuthenticationResponse? = authenticationService.refreshToken(request)
        if(response != null) {
            return ResponseEntity(response, HttpStatus.OK)
        }
        return ResponseEntity(HttpStatus.UNAUTHORIZED)
    }
}