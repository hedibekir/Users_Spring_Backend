package com.expatrio.api.model

import com.expatrio.api.model.dto.UserAccountDto
import com.expatrio.api.tables.records.UserAccountRecord

fun UserAccountRecord.toUserAccount(): UserAccount {
    return UserAccount(id, email, firstname, lastname, password, role)
}

fun UserAccountRecord.toUserAccountDto(): UserAccountDto {
    return UserAccountDto(id, email, firstname, lastname, null)
}

