package com.expatrio.api.model.response

data class AuthenticationResponse(
    val accessToken: String,
    val refreshToken: String
)
