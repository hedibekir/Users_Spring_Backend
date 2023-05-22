package com.expatrio.api.model.dto

import com.expatrio.api.model.UserRole
import com.expatrio.api.tables.records.UserAccountRecord
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.security.crypto.password.PasswordEncoder

data class UserAccountDto(
    val userId: Int?,
    @field:NotNull(message = "Email cannot be null")
    @field:Email(message = "Email must be valid")
    val userEmail: String?,
    @field:NotNull(message = "First Name cannot be null")
    @field:Size(min = 2, max = 50, message = "First Name must be between 2 and 50 characters")
    val userFirstName: String?,
    @field:NotNull(message = "Last Name cannot be null")
    @field:Size(min = 2, max = 50, message = "Last Name must be between 2 and 50 characters")
    val userLastName: String?,
    @field:NotNull(message = "Password cannot be null")
    @field:Size(min = 4, max = 50, message = "Password must be between 4 and 50 characters")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    val userPassword: String?
)

fun UserAccountDto.toUserAccountRecord(
     passwordEncoder: PasswordEncoder?,
     isAdmin: Boolean
): UserAccountRecord {
    val encodedPassword = passwordEncoder?.encode(userPassword)
    val userRole = if(isAdmin) UserRole.ADMIN else UserRole.CUSTOMER
    return UserAccountRecord(userId, userEmail, userFirstName, userLastName, encodedPassword, userRole)
}



