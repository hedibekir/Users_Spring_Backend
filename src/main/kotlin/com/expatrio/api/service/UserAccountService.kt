package com.expatrio.api.service

import com.expatrio.api.model.UserRole
import com.expatrio.api.model.dto.UserAccountDto
import com.expatrio.api.model.dto.toUserAccountRecord
import com.expatrio.api.model.toUserAccountDto
import com.expatrio.api.repository.UserAccountRepository
import com.expatrio.api.tables.records.UserAccountRecord
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserAccountService(
    private val userAccountRepository: UserAccountRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun findAllUsersAccounts(): List<UserAccountDto> =
        userAccountRepository.findAll().map { it.toUserAccountDto() }

    fun findUserAccountById(id: Int): UserAccountDto? {
        val userAccountRecord = userAccountRepository.findById(id)
        if(userAccountRecord != null) {
            return userAccountRecord.toUserAccountDto()
        }
        return null
    }

    fun findUserAccountByRole(role: UserRole): UserAccountRecord? {
       return userAccountRepository.findByRole(role)
    }

    fun insertNewUserAccount(userAccountDto: UserAccountDto, isAdmin: Boolean): Int {
         return userAccountRepository.insert(userAccountDto.toUserAccountRecord(passwordEncoder, isAdmin))
    }

    fun updateUserAccount(userAccountId: Int, userAccountDto: UserAccountDto): Int {
        return userAccountRepository.update(userAccountId, userAccountDto.toUserAccountRecord(passwordEncoder, false))
    }

    fun deleteUserAccount(id: Int): Int {
        return userAccountRepository.delete(id)
    }
}