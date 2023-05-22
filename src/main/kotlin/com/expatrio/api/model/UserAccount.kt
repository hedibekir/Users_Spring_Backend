package com.expatrio.api.model

import com.expatrio.api.tables.records.UserAccountRecord
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class UserAccount(
    val id: Int,
    val email: String,
    val firstname: String,
    val lastName: String,
    private val password: String,
    val role: UserRole
): UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_${role.name}"))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
       return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun toUserAccountRecord(): UserAccountRecord {
        return UserAccountRecord(id, email, firstname, lastName, password, role)
    }

}
