package com.expatrio.api.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class AuthenticationRequest(
    @field:NotNull(message = "Email cannot be null")
    @field:Email(message = "Email must be valid")
    val email: String,
    @field:NotNull(message = "Password cannot be null")
    @field:Size(min = 4, max = 50, message = "Password must be between 4 and 50 characters")
    val password: String
)
