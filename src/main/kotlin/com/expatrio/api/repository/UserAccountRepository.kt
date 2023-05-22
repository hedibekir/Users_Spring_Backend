package com.expatrio.api.repository


import com.expatrio.api.model.UserRole
import com.expatrio.api.tables.UserAccount
import com.expatrio.api.tables.records.UserAccountRecord
import jakarta.transaction.Transactional
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
@Transactional
class UserAccountRepository(private val jooqDsl: DSLContext) {

    fun findAll(): List<UserAccountRecord> =
        jooqDsl.selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.ROLE.equal(UserRole.CUSTOMER))
            .fetch()
            .into(UserAccountRecord::class.java)

    fun findById(id: Int): UserAccountRecord? =
        jooqDsl.selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.ID.equal(id))
            .and(USER_ACCOUNT.ROLE.equal(UserRole.CUSTOMER))
            .fetchOptional()
            .orElse(null)

    fun findByEmail(email: String): UserAccountRecord? =
        jooqDsl.selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.EMAIL.equal(email))
            .fetchOptional()
            .orElse(null)

    fun findByRole(userRole: UserRole): UserAccountRecord? =
        jooqDsl.selectFrom(USER_ACCOUNT)
            .where(USER_ACCOUNT.ROLE.equal(userRole))
            .fetchOptional()
            .orElse(null)

    fun insert(userAccountRecord: UserAccountRecord) =
        jooqDsl.insertInto(USER_ACCOUNT)
            .set(userAccountRecord)
            .execute()

    fun update(userAccountId: Int, userAccountRecord: UserAccountRecord) =
        jooqDsl.update(USER_ACCOUNT)
            .set(userAccountRecord)
            .where(USER_ACCOUNT.ID.eq(userAccountId))
            .and(USER_ACCOUNT.ROLE.equal(UserRole.CUSTOMER))
            .execute()

    fun delete(id: Int) =
        jooqDsl.delete(USER_ACCOUNT)
            .where(USER_ACCOUNT.ID.equal(id))
            .and(USER_ACCOUNT.ROLE.equal(UserRole.CUSTOMER))
            .execute()


    companion object {
        private val USER_ACCOUNT = UserAccount.USER_ACCOUNT
    }


}