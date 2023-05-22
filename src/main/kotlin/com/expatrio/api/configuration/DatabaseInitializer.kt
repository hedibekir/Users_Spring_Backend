package com.expatrio.api.configuration

import com.expatrio.api.model.UserRole
import com.expatrio.api.model.dto.UserAccountDto
import com.expatrio.api.service.UserAccountService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class DatabaseInitializer(
    private val userAccountService: UserAccountService
): CommandLineRunner {
    override fun run(vararg args: String?) {
        logger.info("START DB INITIALIZER")
        val userAccountRecord = userAccountService.findUserAccountByRole(UserRole.ADMIN)
        if(userAccountRecord == null) {
            userAccountService.insertNewUserAccount(
                UserAccountDto(
                    null,
                    "admin@test.com",
                    "admin",
                    "admin",
                    "admin"
                ),
                true
            )
        }
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(this::class.java)
    }
}