package com.expatrio.api.configuration

import com.expatrio.api.model.toUserAccount
import com.expatrio.api.repository.UserAccountRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
class ApplicationConfig (
    private val userAccountRepository: UserAccountRepository
) {

    @Bean
    fun userDetailsService(): UserDetailsService? {
        return UserDetailsService { username: String ->
            userAccountRepository.findByEmail(username)?.toUserAccount() ?: throw UsernameNotFoundException("User Not found")
        }
    }

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider  {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(
        authConfig: AuthenticationConfiguration
    ): AuthenticationManager? {
        return authConfig.authenticationManager
    }
}